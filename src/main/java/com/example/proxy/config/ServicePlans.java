package com.example.proxy.config;

import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicePlans {

    @Bean
    public Catalog catalog() {
        Plan sampleFreePlan = Plan.builder()
                .id("fd81196c-a414-43e5-bd81-1dbb082a3c55")
                .name("sample-free-plan")
                .description("Sample Service Free Plan")
                .free(true)
                .build();

        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
                .id("b92c0ca7-c162-4029-b567-0d92978c0a97")
                .name("sample-service")
                .description("Sample Service")
                .bindable(true)
                .tags("sample", "service")
                .plans(sampleFreePlan)
                .build();

        return Catalog.builder()
                .serviceDefinitions(serviceDefinition)
                .build();
    }
}
