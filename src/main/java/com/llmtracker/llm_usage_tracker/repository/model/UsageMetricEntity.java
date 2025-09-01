package com.llmtracker.llm_usage_tracker.repository.model;

import java.time.Instant;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usage_metric")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsageMetricEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;
    private String orgName;

    private String userId;
    private String userEmail;

    private String vendorName;
    private String vendorModel;
    private String vendorApiType;

    private Double totalUsage;
    private Integer inputTokens;
    private Integer outputTokens;
    private Integer cachedTokens;

    private Integer numberOfImages;
    private Integer videoMinutes;
    private Double audioMinutes;

    private Instant timestamp;
}
