package com.llmtracker.llm_usage_tracker.repository.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor_pricing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorPricingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vendorName;
    private String modelName;
    private String metric;
    private BigDecimal pricePerUnit;
}

