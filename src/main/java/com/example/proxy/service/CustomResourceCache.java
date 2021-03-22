package com.example.proxy.service;

import com.example.proxy.model.CustomResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomResourceCache {
    private Map<String, CustomResource> cache = new HashMap<>();
    private Map<UUID, CustomResource> provisionedResources = new HashMap<>();
    private Map<UUID, String> provisionedAsboBindings = new HashMap<>();

    public Map<String, CustomResource> getCache(){
        return cache;
    }

    public Map<String, CustomResource> add(String planId, CustomResource customResource){
        cache.put(planId, customResource);
        return cache;
    }

    public void addToProvisionedResources(UUID serviceInstanceId, CustomResource customResource){
        CustomResource customResource1 = new CustomResource();
        customResource1.setApiVersion(customResource.getApiVersion());
        customResource1.setKind(customResource.getKind());
        customResource1.setPlural(customResource.getPlural());
        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.putAll(customResource.getMetadata());
        customResource1.setMetadata(metadataMap);

        Map<String, Object> specMap = new HashMap<>();
        specMap.putAll(customResource.getSpec());
        customResource1.setSpec(specMap);
        provisionedResources.put(serviceInstanceId, customResource1);
    }

    public CustomResource getProvisionedResource(UUID serviceInstanceId){
        return provisionedResources.get(serviceInstanceId);
    }

    public void addToProvisionedAsboBindings(UUID bindingId, String name){
        provisionedAsboBindings.put(bindingId, name);
    }

    public String getProvisionedAsboBinding(UUID bindingId){
        return provisionedAsboBindings.get(bindingId);
    }

}
