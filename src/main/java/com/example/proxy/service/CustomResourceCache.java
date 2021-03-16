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

    /*public CustomResource mapping(){
        CustomResource crSampleOperator = new CustomResource();
        crSampleOperator.setApiVersion("sample-operator.example.com/v1");
        crSampleOperator.setKind("SampleOperator");
        crSampleOperator.setPlural("sampleoperators");
        return crSampleOperator;
    }*/

    public static void main (String args[]){
        CustomResource crSampleOperator = new CustomResource();
        crSampleOperator.setApiVersion("sample-operator.example.com/v1");
        crSampleOperator.setKind("SampleOperator");
        crSampleOperator.setPlural("sampleoperators");
        HashMap<String, Object> metadata = new HashMap<>();
        metadata.put("name", "sampleoperator-sample");
        metadata.put("namespace", "default");

        HashMap<String, Object> spec  = new HashMap<>();
        spec.put("serviceInstanceName", "sample-instance1");
        crSampleOperator.setMetadata(metadata);
        crSampleOperator.setSpec(spec);
        CustomResourceCache customResourceCache = new CustomResourceCache();
        customResourceCache.addToProvisionedResources(UUID.randomUUID(), crSampleOperator);
    }
}
