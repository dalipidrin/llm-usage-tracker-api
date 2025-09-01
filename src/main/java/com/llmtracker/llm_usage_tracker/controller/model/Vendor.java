package com.llmtracker.llm_usage_tracker.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @NotBlank
    private String name;

    @NotBlank
    private String model;

    @NotBlank
    private String apiType;
}
