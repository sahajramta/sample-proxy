package com.example.proxy.api;

import com.example.proxy.model.CustomResource;
import com.example.proxy.model.ServiceModel;
import com.example.proxy.service.CustomResourceCache;
import com.example.proxy.service.OperatorService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.binding.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProxyBindingInstanceApi implements ServiceInstanceBindingService {

    @Autowired
    private CustomResourceCache customResourceCache;
    @Autowired
    private OperatorService operatorService;

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
        log.info("CreateServiceInstanceBindingRequest");
        log.info(request.toString());
        CustomResource customResource = customResourceCache
                .getProvisionedResource(UUID.fromString(request.getServiceInstanceId()));

        HashMap<String, Object> body = new HashMap<>();
        body.put("apiVersion", "binding.operators.coreos.com/v1alpha1");
        body.put("kind", "ServiceBinding");

        String serviceBindingName = request.getContext().getProperties().get("instance_name") + "-service-binding";
        HashMap<String, Object> metadata = new HashMap<>();
        metadata.put("name", request.getContext().getProperties().get("instance_name"));
        metadata.put("namespace", "default");
        body.put("metadata", metadata);

        HashMap<String, Object> spec = new HashMap<>();

        HashMap<String, Object> application = new HashMap<>();
        application.put("name", request.getParameters().get("appName"));
        application.put("group", request.getParameters().get("group"));
        application.put("version", request.getParameters().get("version"));
        application.put("resource", request.getParameters().get("resource"));
        spec.put("application", application);

        List<ServiceModel> services = new ArrayList<>();
        ServiceModel serviceModel = new ServiceModel();
        String[] groupVersionArr = customResource.getApiVersion().split("/");
        serviceModel.setGroup(groupVersionArr[0]);
        serviceModel.setVersion(groupVersionArr[1]);
        serviceModel.setKind(customResource.getKind());
        serviceModel.setName(customResource.getMetadata().get("name").toString());
        //serviceModel.setId("postgresDB");
        services.add(serviceModel);
        spec.put("services", services);
        body.put("spec", spec);

        Gson gson = new Gson();
        log.info(gson.toJson(body));

        try {
            operatorService.bindService(body);
        } catch (IOException e) {
            log.error("IOException", e);
        }
        return CreateServiceInstanceAppBindingResponse.builder().build();
    }

    @Override
    public GetServiceInstanceBindingResponse getServiceInstanceBinding(GetServiceInstanceBindingRequest request) {
        return null;
    }

    @Override
    public GetLastServiceBindingOperationResponse getLastOperation(GetLastServiceBindingOperationRequest request) {
        return null;
    }

    @Override
    public DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest deleteServiceInstanceBindingRequest) {
        return DeleteServiceInstanceBindingResponse.builder().build();
    }
}
