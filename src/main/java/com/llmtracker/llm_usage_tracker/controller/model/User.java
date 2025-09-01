package com.llmtracker.llm_usage_tracker.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;
}
