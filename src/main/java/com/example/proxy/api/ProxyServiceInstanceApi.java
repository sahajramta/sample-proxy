package com.example.proxy.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.proxy.model.CustomResource;
import com.example.proxy.service.CustomResourceCache;
import com.example.proxy.service.OperatorService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.instance.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProxyServiceInstanceApi implements ServiceInstanceService {

    @Autowired
    private CustomResourceCache customResourceCache;
    @Autowired
    private OperatorService operatorService;

    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
        try {
            log.info(request.toString());
            CustomResource customResource = customResourceCache.getCache().get(request.getPlanId());

            Map<String, Object> contextPrpMap = request
                    .getContext()
                    .getProperties();
            String instanceName = contextPrpMap.get("instance_name").toString();
            String namespace = contextPrpMap.get("namespace").toString();

            HashMap<String, Object> metadata = new HashMap<>();
            metadata.put("name", instanceName + "-cr-instance");
            metadata.put("namespace", namespace);
            customResource.setMetadata(metadata);
            customResource.setSpec(request.getParameters());
            //customResource.setPlural("sampleoperators");
            operatorService.createService(customResource);
            customResourceCache.addToProvisionedResources(UUID.fromString(request.getServiceInstanceId()), customResource);
        } catch (IOException e) {
            log.error("Exception while creating Service Instance", e);
        }
        return CreateServiceInstanceResponse.builder().build();
    }

    @Override
    public GetServiceInstanceResponse getServiceInstance(GetServiceInstanceRequest request) {
        return null;
    }

    @Override
    public GetLastServiceOperationResponse getLastOperation(GetLastServiceOperationRequest request) {
        return null;
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
        log.info(request.toString());
        CustomResource customResource = customResourceCache.getProvisionedResource(UUID.fromString(request.getServiceInstanceId()));
        try {
            if(customResource != null){
                operatorService.deleteServiceInstance(customResource);
            }
        } catch (IOException e) {
            log.error("Exception while deleting Service Instance", e);
        }
        return DeleteServiceInstanceResponse.builder().build();
    }

    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
        return null;
    }
}
