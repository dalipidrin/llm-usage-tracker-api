package com.llmtracker.llm_usage_tracker.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.llmtracker.llm_usage_tracker.controller.model.Usage;
import com.llmtracker.llm_usage_tracker.controller.model.UsageEventRequest;
import com.llmtracker.llm_usage_tracker.service.model.UsageMetric;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsageEventNormalizeMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "orgName", source = "customer.orgName")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "vendorName", source = "vendor.name")
    @Mapping(target = "vendorModel", source = "vendor.model")
    @Mapping(target = "vendorApiType", source = "vendor.apiType")

    // Normalized metrics
    @Mapping(target = "totalUsage", expression = "java(normalizeTotal(request.getUsage(), request.getVendor().getName()))")
    @Mapping(target = "inputTokens", expression = "java(normalizeInput(request.getUsage(), request.getVendor().getName()))")
    @Mapping(target = "outputTokens", expression = "java(normalizeOutput(request.getUsage(), request.getVendor().getName()))")
    @Mapping(target = "cachedTokens", source = "usage.cachedTokens")

    @Mapping(target = "numberOfImages", expression = "java(normalizeNumberOfImages(request.getUsage(), request.getVendor().getName()))")
    @Mapping(target = "videoMinutes", expression = "java(normalizeVideoMinutes(request.getUsage(), request.getVendor().getName()))")
    @Mapping(target = "audioMinutes", expression = "java(normalizeAudioMinutes(request.getUsage(), request.getVendor().getName()))")
    @Mapping(target = "timestamp", source = "timestamp")
    UsageMetric map(UsageEventRequest request);

    // Normalization logic

    default Double normalizeTotal(Usage usage, String vendor) {
        return switch (vendor.toLowerCase()) {
            case "openai" -> sumTokens(usage.getPromptTokens(), usage.getCompletionTokens(), usage.getNumberOfImages(),
                    usage.getVideoDuration(), usage.getAudioDuration());
            case "anthropic" -> sumTokens(usage.getInputTokensRaw(), usage.getOutputTokensRaw(), usage.getImagesRaw(),
                    usage.getVideoDuration(), usage.getAudioDuration());
            case "gemini" -> sumTokens(usage.getTextTokens(), usage.getNrImages(), usage.getVideoLengthInMinutes(),
                    0, usage.getAudioLengthInMinutes());
            default -> 0.0;
        };
    }

    default Integer normalizeInput(Usage usage, String vendor) {
        return switch (vendor.toLowerCase()) {
            case "openai" -> usage.getPromptTokens() != null ? usage.getPromptTokens() : 0;
            case "anthropic" -> usage.getInputTokensRaw() != null ? usage.getInputTokensRaw() : 0;
            case "gemini" -> usage.getTextTokens() != null ? usage.getTextTokens() : 0;
            default -> 0;
        };
    }

    default Integer normalizeOutput(Usage usage, String vendor) {
        return switch (vendor.toLowerCase()) {
            case "openai" -> usage.getCompletionTokens() != null ? usage.getCompletionTokens() : 0;
            case "anthropic" -> usage.getOutputTokensRaw() != null ? usage.getOutputTokensRaw() : 0;
            default -> 0;
        };
    }

    default Integer normalizeNumberOfImages(Usage usage, String vendor) {
        return switch (vendor.toLowerCase()) {
            case "openai" -> usage.getNumberOfImages() != null ? usage.getNumberOfImages() : 0;
            case "anthropic" -> usage.getImagesRaw() != null ? usage.getImagesRaw() : 0;
            case "gemini" -> usage.getNrImages() != null ? usage.getNrImages() : 0;
            default -> 0;
        };
    }

    default Integer normalizeVideoMinutes(Usage usage, String vendor) {
        return switch (vendor.toLowerCase()) {
            case "openai" -> usage.getVideoDuration() != null ? usage.getVideoDuration() : 0;
            case "anthropic" -> usage.getVideoMinutesRaw() != null ? usage.getVideoMinutesRaw() : 0;
            case "gemini" -> usage.getVideoLengthInMinutes() != null ? usage.getVideoLengthInMinutes() : 0;
            default -> 0;
        };
    }

    default Double normalizeAudioMinutes(Usage usage, String vendor) {
        return switch (vendor.toLowerCase()) {
            case "openai" -> usage.getAudioDuration() != null ? usage.getAudioDuration() : 0.0;
            case "anthropic" -> usage.getAudioMinutesRaw() != null ? usage.getAudioMinutesRaw() : 0.0;
            case "gemini" -> usage.getAudioLengthInMinutes() != null ? usage.getAudioLengthInMinutes() : 0.0;
            default -> 0.0;
        };
    }

    default Double sumTokens(Integer a, Integer b, Integer c, Integer d, Double e) {
        return (a != null ? a : 0) + (b != null ? b : 0) + (c != null ? c : 0) + (d != null ? d : 0) + (e != null ? e : 0);
    }
}


