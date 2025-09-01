package com.llmtracker.llm_usage_tracker.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.llmtracker.llm_usage_tracker.controller.model.PeriodValue;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PeriodValueMapper {

    PeriodValue map(com.llmtracker.llm_usage_tracker.service.model.PeriodValue periodValue);

    List<PeriodValue> map(List<com.llmtracker.llm_usage_tracker.service.model.PeriodValue> periodValue);

    default Map<String, List<PeriodValue>> map(Map<String, List<com.llmtracker.llm_usage_tracker.service.model.PeriodValue>> serviceMap) {
        Map<String, List<PeriodValue>> result = new HashMap<>();
        serviceMap.forEach((key, valueList) -> result.put(key, map(valueList)));
        return result;
    }
}