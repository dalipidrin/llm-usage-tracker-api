package com.llmtracker.llm_usage_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        String allowedOrigin = System.getenv("ALLOWED_ORIGIN");
        if (allowedOrigin == null || allowedOrigin.isBlank()) {
            allowedOrigin = "http://localhost:5173";
        }
        final String finalAllowedOrigin = allowedOrigin;

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(finalAllowedOrigin)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}

