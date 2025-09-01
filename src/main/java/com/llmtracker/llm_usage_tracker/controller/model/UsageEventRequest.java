package com.llmtracker.llm_usage_tracker.controller.model;

import java.time.Instant;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsageEventRequest {

    @Valid
    @NotNull
    private Customer customer;

    @Valid
    @NotNull
    private User user;

    @Valid
    @NotNull
    private Vendor vendor;

    @Valid
    @NotNull
    private Usage usage;

    @NotNull
    private Instant timestamp;
}
