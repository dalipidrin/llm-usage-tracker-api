package com.llmtracker.llm_usage_tracker.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPricing {

    private String customerId;

    private Double markupPercent;
}
