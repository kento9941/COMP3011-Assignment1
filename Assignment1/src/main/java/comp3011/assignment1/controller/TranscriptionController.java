package comp3011.assignment1.controller;

import comp3011.assignment1.service.TranscriptionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class TranscriptionController {

    private final TranscriptionService transcriptionService;

    public TranscriptionController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @PostMapping("/api/transcribe")
    public Map<String, String> transcribe(@RequestParam("audio") MultipartFile audio) {
        String text = transcriptionService.transcribe(audio);
        return Map.of("text", text);
    }
}