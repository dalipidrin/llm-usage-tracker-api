package com.llmtracker.llm_usage_tracker.mapping;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.llmtracker.llm_usage_tracker.controller.model.SummaryMetric;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SummaryMetricMapper {

    SummaryMetric map(com.llmtracker.llm_usage_tracker.service.model.SummaryMetric summaryMetric);

    List<SummaryMetric> map(List<com.llmtracker.llm_usage_tracker.service.model.SummaryMetric> summaryMetrics);
}