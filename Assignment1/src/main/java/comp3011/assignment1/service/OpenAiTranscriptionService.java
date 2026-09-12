package comp3011.assignment1.service;

import comp3011.assignment1.dto.OpenAiTranscriptionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Profile("!stub")
public class OpenAiTranscriptionService implements TranscriptionService {

    private static final String OPENAI_URL = "https://api.openai.com/v1/audio/transcriptions";
    private static final String MODEL = "gpt-4o-mini-transcribe";

    private final RestClient restClient;
    private final String apiKey;

    public OpenAiTranscriptionService(@Value("${OPENAI_API_KEY}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient = RestClient.create();
    }

    @Override
    public String transcribe(MultipartFile audio) {
        byte[] audioBytes;
        try {
            audioBytes = audio.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read uploaded audio data", e);
        }

        ByteArrayResource fileResource = new ByteArrayResource(audioBytes) {
            @Override
            public String getFilename() {
                return "recording.webm";
            }
        };

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("model", MODEL);
        form.add("file", fileResource);

        OpenAiTranscriptionResponse response = restClient.post()
                .uri(OPENAI_URL)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(form)
                .retrieve()
                .body(OpenAiTranscriptionResponse.class);

        return response != null ? response.text() : "";
    }
}