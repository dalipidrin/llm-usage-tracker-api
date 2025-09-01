package com.llmtracker.llm_usage_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.llmtracker.llm_usage_tracker.repository.model.CustomerPricingEntity;

@Repository
public interface CustomerPricingRepository extends JpaRepository<CustomerPricingEntity, Long> {
    Optional<CustomerPricingEntity> findByCustomerId(String customerId);
}
