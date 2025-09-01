package com.llmtracker.llm_usage_tracker.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.llmtracker.llm_usage_tracker.mapping.CustomerPricingMapper;
import com.llmtracker.llm_usage_tracker.mapping.UsageMetricEntityMapper;
import com.llmtracker.llm_usage_tracker.mapping.UsageMetricMapper;
import com.llmtracker.llm_usage_tracker.mapping.VendorPricingMapper;
import com.llmtracker.llm_usage_tracker.repository.CustomerPricingRepository;
import com.llmtracker.llm_usage_tracker.repository.UsageMetricRepository;
import com.llmtracker.llm_usage_tracker.repository.VendorPricingRepository;
import com.llmtracker.llm_usage_tracker.repository.model.UsageMetricEntity;
import com.llmtracker.llm_usage_tracker.service.UsageMetricsService;
import com.llmtracker.llm_usage_tracker.service.model.CustomerPricing;
import com.llmtracker.llm_usage_tracker.service.model.PeriodValue;
import com.llmtracker.llm_usage_tracker.service.model.SummaryMetric;
import com.llmtracker.llm_usage_tracker.service.model.UsageMetric;
import com.llmtracker.llm_usage_tracker.service.model.VendorPricing;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsageMetricsServiceImpl implements UsageMetricsService {

    private final UsageMetricRepository usageMetricRepository;

    private final CustomerPricingRepository customerPricingRepository;

    private final UsageMetricEntityMapper usageMetricEntityMapper;

    private final VendorPricingRepository vendorPricingRepository;

    private final UsageMetricMapper usageMetricMapper;

    private final CustomerPricingMapper customerPricingMapper;

    private final VendorPricingMapper vendorPricingMapper;

    @Override
    public Long processUsageEvent(UsageMetric usageMetric) {
        UsageMetricEntity persistedUsageEventEntity = usageMetricRepository.save(usageMetricEntityMapper.map(usageMetric));
        return persistedUsageEventEntity.getId();
    }

    @Override
    public List<SummaryMetric> getSummaryMetrics(
            String customerId,
            String userId,
            String vendorName,
            String apiType,
            Instant fromDate,
            Instant toDate) {

        // Fetch filtered usage metrics from DB
        List<UsageMetric> usageMetrics = usageMetricMapper.map(usageMetricRepository.getUsageMetrics(
                customerId, userId, vendorName, apiType, fromDate, toDate
        ));

        // Prepare result map grouped by customer + user
        Map<String, SummaryMetric> aggregationMap = new HashMap<>();

        for (UsageMetric metric : usageMetrics) {

            // Compute cost using CustomerPricing
            Optional<CustomerPricing> customerPricing = customerPricingRepository
                    .findByCustomerId(metric.getCustomerId())
                    .map(customerPricingMapper::map);

            List<VendorPricing> vendorPricingList =
                    vendorPricingMapper.map(vendorPricingRepository.findByVendorName(metric.getVendorName()));

            Map<String, BigDecimal> vendorPricingMap = vendorPricingList.stream()
                    .collect(Collectors.toMap(VendorPricing::getMetric, VendorPricing::getPricePerUnit));

            double costPerEvent = 0.0;
            costPerEvent += (metric.getInputTokens() != null)
                    ? metric.getInputTokens() * vendorPricingMap.getOrDefault("input_tokens", BigDecimal.ZERO).doubleValue()
                    : 0;
            costPerEvent += (metric.getOutputTokens() != null)
                    ? metric.getOutputTokens() * vendorPricingMap.getOrDefault("output_tokens", BigDecimal.ZERO).doubleValue()
                    : 0;
            costPerEvent += (metric.getNumberOfImages() != null)
                    ? metric.getNumberOfImages() * vendorPricingMap.getOrDefault("images", BigDecimal.ZERO).doubleValue()
                    : 0;
            costPerEvent += (metric.getAudioMinutes() != null)
                    ? metric.getAudioMinutes() * vendorPricingMap.getOrDefault("audio_minutes", BigDecimal.ZERO).doubleValue()
                    : 0;
            costPerEvent += (metric.getVideoMinutes() != null)
                    ? metric.getVideoMinutes() * vendorPricingMap.getOrDefault("video_minutes", BigDecimal.ZERO).doubleValue()
                    : 0;

            // Apply markup for revenue
            double markupPercent = customerPricing
                    .map(CustomerPricing::getMarkupPercent)
                    .orElse(0.0);
            double revenue = costPerEvent * (1 + markupPercent / 100.0);
            double profit = revenue - costPerEvent;

            // Aggregate key: customerId + userId
            String key = metric.getCustomerId() + "::" + metric.getUserId();

            SummaryMetric agg = aggregationMap.getOrDefault(key,
                    new SummaryMetric(
                            metric.getCustomerId(),
                            metric.getOrgName(),
                            metric.getUserId(),
                            metric.getUserEmail(),
                            metric.getVendorName(),
                            metric.getVendorApiType(),
                            0.0, 0.0, 0.0, 0,
                            metric.getTimestamp()
                    )
            );

            // Update aggregated values
            agg.setTotalCost(agg.getTotalCost() + costPerEvent);
            agg.setTotalRevenue(agg.getTotalRevenue() + revenue);
            agg.setTotalProfit(agg.getTotalProfit() + profit);
            agg.setTotalUsage(agg.getTotalUsage() +
                    (metric.getInputTokens() != null ? metric.getInputTokens() : 0) +
                    (metric.getOutputTokens() != null ? metric.getOutputTokens() : 0) +
                    (metric.getCachedTokens() != null ? metric.getCachedTokens() : 0) +
                    (metric.getAudioMinutes() != null ? metric.getAudioMinutes() : 0) +
                    (metric.getVideoMinutes() != null ? metric.getVideoMinutes() : 0) +
                    (metric.getNumberOfImages() != null ? metric.getNumberOfImages() : 0)
            );

            aggregationMap.put(key, agg);
        }

        return new ArrayList<>(aggregationMap.values());
    }

    @Override
    public Map<String, List<PeriodValue>> getGroupedMetrics(String groupBy, String period) {
        if (groupBy == null || period == null) {
            throw new IllegalArgumentException("groupBy and period parameters are required");
        }

        List<Object[]> rawData = usageMetricRepository.aggregateMetrics(groupBy, period);

        Map<String, List<PeriodValue>> result = new HashMap<>();

        for (Object[] row : rawData) {
            String groupValue = (String) row[0];
            String periodValue = (String) row[1];
            double metricValue = ((Number) row[2]).doubleValue();

            result.computeIfAbsent(groupValue, k -> new ArrayList<>())
                    .add(new PeriodValue(periodValue, metricValue));
        }

        return result;
    }
}
