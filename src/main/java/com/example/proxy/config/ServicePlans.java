package com.example.proxy.config;

import org.springframework.cloud.servicebroker.model.catalog.*;
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
                .metadata("serviceInstanceName", "sample-service-instance-1")
                .schemas(Schemas
                        .builder()
                        .serviceInstanceSchema(ServiceInstanceSchema
                                .builder()
                                .createMethodSchema(MethodSchema
                                        .builder()
                                        .parameters("$schema", "http://json-schema.org/draft-04/schema#")
                                        .parameters("type", "object")
                                        .parameters("", "")
                                        .build())
                                .build())
                        .build())
                .build();

        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
                .id("b92c0ca7-c162-4029-b567-0d92978c0a97")
                .name("sample-service")
                .description("Sample Service")
                .bindable(true)
                .tags("sample", "service")
                .plans(sampleFreePlan)
                .metadata("displayName", "sample-broker")
                .metadata("longDescription", "A OSB broker interacting with operator service")
                .metadata("providerDisplayName", "Sample Broker")
                .build();

        return Catalog.builder()
                .serviceDefinitions(serviceDefinition)
                .build();
    }
}
