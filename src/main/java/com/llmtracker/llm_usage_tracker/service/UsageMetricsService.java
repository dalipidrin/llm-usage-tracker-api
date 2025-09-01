package com.llmtracker.llm_usage_tracker.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.llmtracker.llm_usage_tracker.service.model.SummaryMetric;
import com.llmtracker.llm_usage_tracker.service.model.PeriodValue;
import com.llmtracker.llm_usage_tracker.service.model.UsageMetric;

public interface UsageMetricsService {

    Long processUsageEvent(UsageMetric usageMetric);

    List<SummaryMetric> getSummaryMetrics(String customerId, String userId, String vendorName, String apiType, Instant startDate,
                                          Instant endDate);

    Map<String, List<PeriodValue>> getGroupedMetrics(String groupBy, String period);
}
