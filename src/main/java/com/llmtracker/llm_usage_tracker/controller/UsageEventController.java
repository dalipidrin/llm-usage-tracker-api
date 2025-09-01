package com.llmtracker.llm_usage_tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llmtracker.llm_usage_tracker.controller.model.UsageEventRequest;
import com.llmtracker.llm_usage_tracker.controller.model.UsageEventResponse;
import com.llmtracker.llm_usage_tracker.mapping.UsageEventNormalizeMapper;
import com.llmtracker.llm_usage_tracker.service.UsageMetricsService;
import com.llmtracker.llm_usage_tracker.service.model.UsageMetric;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usage")
@RequiredArgsConstructor
public class UsageEventController {

    private final UsageMetricsService usageMetricsService;

    private final UsageEventNormalizeMapper usageEventNormalizeMapper;

    @PostMapping
    public ResponseEntity<UsageEventResponse> createUsageEvent(@Valid @RequestBody UsageEventRequest usageEventRequest) {
        UsageMetric usageMetric = usageEventNormalizeMapper.map(usageEventRequest);
        Long usageEventId = usageMetricsService.processUsageEvent(usageMetric);
        UsageEventResponse usageEventResponse = new UsageEventResponse().setId(usageEventId);
        return ResponseEntity.ok(usageEventResponse);
    }
}
