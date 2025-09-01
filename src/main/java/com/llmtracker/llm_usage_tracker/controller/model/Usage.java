package com.llmtracker.llm_usage_tracker.controller.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usage {

    // General metrics
    @Min(0)
    private Double totalUsage;
    @Min(0)
    private Integer inputTokens;
    @Min(0)
    private Integer outputTokens;
    @Min(0)
    private Integer cachedTokens;

    @Min(0)
    private Integer imageRequests;
    @Min(0)
    private Integer videoRequests;
    @DecimalMin("0.0")
    private Double audioMinutes;

    // Vendor-specific metrics

    // Text tokens
    private Integer promptTokens;       // OpenAI (input)
    private Integer completionTokens;   // OpenAI (output)
    private Integer inputTokensRaw;     // Anthropic (input)
    private Integer outputTokensRaw;    // Anthropic (output)
    private Integer textTokens;         // Gemini

    // Image metrics
    private Integer numberOfImages;     // OpenAI
    private Integer imagesRaw;          // Anthropic
    private Integer nrImages;           // Gemini

    // Video metrics
    private Integer videoDuration;         // OpenAI
    private Integer videoMinutesRaw;       // Anthropic
    private Integer videoLengthInMinutes;  // Gemini

    // Audio metrics
    private Double audioDuration;         // OpenAI
    private Double audioMinutesRaw;       // Anthropic
    private Double audioLengthInMinutes;  // Gemini
}


