package com.llmtracker.llm_usage_tracker.controller.model;

import java.util.Map;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupedMetricsResponse {
    private Map<String, List<PeriodValue>> data;
}