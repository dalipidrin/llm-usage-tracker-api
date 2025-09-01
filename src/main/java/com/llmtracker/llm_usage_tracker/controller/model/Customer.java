package com.llmtracker.llm_usage_tracker.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @NotBlank
    private String id;

    @NotBlank
    private String orgName;
}
