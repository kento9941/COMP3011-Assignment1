package comp3011.assignment1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StubTranscriptionService implements TranscriptionService {

    @Override
    public String transcribe(MultipartFile audio) {
        return "Stub transcription received " + audio.getSize() + " bytes.";
    }
}