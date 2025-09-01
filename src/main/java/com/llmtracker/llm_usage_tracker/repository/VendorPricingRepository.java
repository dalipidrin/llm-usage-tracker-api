package com.llmtracker.llm_usage_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.llmtracker.llm_usage_tracker.repository.model.VendorPricingEntity;

@Repository
public interface VendorPricingRepository extends JpaRepository<VendorPricingEntity, Long> {

    List<VendorPricingEntity> findByVendorName(String vendorName);
}
