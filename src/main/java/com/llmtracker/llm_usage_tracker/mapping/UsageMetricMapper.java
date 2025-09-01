package com.llmtracker.llm_usage_tracker.mapping;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.llmtracker.llm_usage_tracker.repository.model.UsageMetricEntity;
import com.llmtracker.llm_usage_tracker.service.model.UsageMetric;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsageMetricMapper {

    UsageMetric map(UsageMetricEntity usageMetricEntity);

    List<UsageMetric> map(List<UsageMetricEntity> usageMetricEntities);
}
