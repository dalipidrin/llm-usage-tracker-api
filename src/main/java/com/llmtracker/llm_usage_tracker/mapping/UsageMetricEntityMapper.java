package com.llmtracker.llm_usage_tracker.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.llmtracker.llm_usage_tracker.repository.model.UsageMetricEntity;
import com.llmtracker.llm_usage_tracker.service.model.UsageMetric;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsageMetricEntityMapper {

    @Mapping(target = "id", ignore = true)
    UsageMetricEntity map(UsageMetric usageMetric);
}