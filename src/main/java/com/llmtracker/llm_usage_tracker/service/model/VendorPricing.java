package com.llmtracker.llm_usage_tracker.service.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorPricing {

    private String vendorName;
    private String modelName;
    private String metric;
    private BigDecimal pricePerUnit;
}
