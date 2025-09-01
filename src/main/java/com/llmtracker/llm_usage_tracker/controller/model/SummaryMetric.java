package com.llmtracker.llm_usage_tracker.controller.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryMetric {

    private String customerId;
    private String orgName;
    private String userId;
    private String userEmail;
    private String vendorName;
    private String apiType;
    private double totalCost;
    private double totalRevenue;
    private double totalProfit;
    private double totalUsage;
    private Instant timestamp;
}