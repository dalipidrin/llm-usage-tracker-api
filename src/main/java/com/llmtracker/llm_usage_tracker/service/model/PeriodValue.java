package com.llmtracker.llm_usage_tracker.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PeriodValue {
    private String period;
    private double value;
}
