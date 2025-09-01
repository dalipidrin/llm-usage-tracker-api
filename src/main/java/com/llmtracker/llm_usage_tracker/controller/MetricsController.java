package com.llmtracker.llm_usage_tracker.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.llmtracker.llm_usage_tracker.controller.model.SummaryMetric;
import com.llmtracker.llm_usage_tracker.controller.model.SummaryMetricsResponse;
import com.llmtracker.llm_usage_tracker.controller.model.GroupedMetricsResponse;
import com.llmtracker.llm_usage_tracker.controller.model.PeriodValue;
import com.llmtracker.llm_usage_tracker.mapping.SummaryMetricMapper;
import com.llmtracker.llm_usage_tracker.mapping.PeriodValueMapper;
import com.llmtracker.llm_usage_tracker.service.UsageMetricsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final UsageMetricsService usageMetricsService;

    private final SummaryMetricMapper summaryMetricMapper;

    private final PeriodValueMapper periodValueMapper;

    @GetMapping("/summary")
    public SummaryMetricsResponse getSummaryMetrics(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String vendorName,
            @RequestParam(required = false) String apiType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        Instant instantFromDate = fromDate != null ? fromDate.atStartOfDay(ZoneOffset.UTC).toInstant() : null;
        Instant instantToDate = toDate != null ? toDate.atStartOfDay(ZoneOffset.UTC).toInstant() : null;
        List<SummaryMetric> summaryMetrics = summaryMetricMapper.map(
                usageMetricsService.getSummaryMetrics(customerId, userId, vendorName, apiType, instantFromDate, instantToDate));
        return new SummaryMetricsResponse(summaryMetrics);
    }

    @GetMapping("/grouped")
    public GroupedMetricsResponse getGroupedMetrics(
            @RequestParam String groupBy,
            @RequestParam String period
    ) {
        Map<String, List<PeriodValue>> groupedMetrics = periodValueMapper.map(usageMetricsService.getGroupedMetrics(groupBy, period));
        return new GroupedMetricsResponse(groupedMetrics);
    }
}
