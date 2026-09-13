package comp3011.assignment1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenAiTranscriptionResponse(String text, Usage usage) {

    // OpenAI's JSON uses snake_case field names.
    // Mapping to camelCase record fields.
    public record Usage(
            @JsonProperty("input_tokens") long inputTokens,
            @JsonProperty("output_tokens") long outputTokens
    ) {}
}