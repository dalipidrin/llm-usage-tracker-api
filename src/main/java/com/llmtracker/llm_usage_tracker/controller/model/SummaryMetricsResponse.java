package com.llmtracker.llm_usage_tracker.controller.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryMetricsResponse {

    List<SummaryMetric> data;
}