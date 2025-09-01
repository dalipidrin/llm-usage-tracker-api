package com.llmtracker.llm_usage_tracker.mapping;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.llmtracker.llm_usage_tracker.repository.model.VendorPricingEntity;
import com.llmtracker.llm_usage_tracker.service.model.VendorPricing;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VendorPricingMapper {

    VendorPricing map(VendorPricingEntity vendorPricingEntity);

    List<VendorPricing> map(List<VendorPricingEntity> vendorPricingEntities);
}
