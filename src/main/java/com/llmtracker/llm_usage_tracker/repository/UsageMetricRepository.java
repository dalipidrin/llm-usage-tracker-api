package com.llmtracker.llm_usage_tracker.repository;

import java.time.Instant;
import java.util.List;

import com.llmtracker.llm_usage_tracker.repository.model.UsageMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageMetricRepository extends JpaRepository<UsageMetricEntity, Long> {

    @Query("""
        SELECT u FROM UsageMetricEntity u
        WHERE (:customerId IS NULL OR u.customerId = :customerId)
          AND (:userId IS NULL OR u.userId = :userId)
          AND (:vendorName IS NULL OR u.vendorName = :vendorName)
          AND (:apiType IS NULL OR u.vendorApiType = :apiType)
          AND (:fromDate IS NULL OR u.timestamp >= :fromDate)
          AND (:toDate IS NULL OR u.timestamp <= :toDate)
        """)
    List<UsageMetricEntity> getUsageMetrics(
            @Param("customerId") String customerId,
            @Param("userId") String userId,
            @Param("vendorName") String vendorName,
            @Param("apiType") String apiType,
            @Param("fromDate") Instant fromDate,
            @Param("toDate") Instant toDate
    );

    @Query("""
        SELECT 
            CASE :groupBy
                WHEN 'vendorName' THEN u.vendorName
                WHEN 'vendorApiType' THEN u.vendorApiType
                WHEN 'vendorModel' THEN u.vendorModel
            END as groupValue,
            FUNCTION('DATE_FORMAT', u.timestamp, 
                     CASE :period
                         WHEN 'day' THEN '%Y-%m-%d'
                         WHEN 'week' THEN '%x-W%v'
                         WHEN 'month' THEN '%Y-%m'
                     END) as periodValue,
            SUM(u.inputTokens + u.outputTokens) as metricValue
        FROM UsageMetricEntity u
        GROUP BY groupValue, periodValue
        ORDER BY periodValue
    """)
    List<Object[]> aggregateMetrics(@Param("groupBy") String groupBy, @Param("period") String period);
}
