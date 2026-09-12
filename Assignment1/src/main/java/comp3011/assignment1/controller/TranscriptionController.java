package comp3011.assignment1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class TranscriptionController {

    @PostMapping("/api/transcribe")
    public Map<String, String> transcribe(@RequestParam("audio") MultipartFile audio) {
        // just proves the multipart upload actually arrives,
        // before wire in the real OpenAI call.
        return Map.of("text", "Stub transcription received " + audio.getSize() + " bytes.");
    }
}