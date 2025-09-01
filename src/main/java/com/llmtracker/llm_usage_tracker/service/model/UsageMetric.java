package com.llmtracker.llm_usage_tracker.service.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsageMetric {

    private String customerId;
    private String orgName;

    private String userId;
    private String userEmail;

    private String vendorName;
    private String vendorModel;
    private String vendorApiType;

    // Normalized metrics
    private Double totalUsage;
    private Integer inputTokens;
    private Integer outputTokens;
    private Integer cachedTokens;

    private Integer numberOfImages;
    private Integer videoMinutes;
    private Double audioMinutes;

    private Instant timestamp;
}


