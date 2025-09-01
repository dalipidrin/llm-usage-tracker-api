package com.llmtracker.llm_usage_tracker.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.llmtracker.llm_usage_tracker.repository.model.CustomerPricingEntity;
import com.llmtracker.llm_usage_tracker.service.model.CustomerPricing;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerPricingMapper {

    CustomerPricing map(CustomerPricingEntity customerPricingEntity);
}
