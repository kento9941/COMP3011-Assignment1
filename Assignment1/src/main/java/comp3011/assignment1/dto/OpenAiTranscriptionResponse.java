package comp3011.assignment1.dto;

// Maps OpenAI's response shape: { "text": "..." }
public record OpenAiTranscriptionResponse(String text) {}