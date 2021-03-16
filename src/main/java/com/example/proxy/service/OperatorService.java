package com.example.proxy.service;

import com.example.proxy.model.CustomResource;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CustomObjectsApi;
import io.kubernetes.client.models.V1DeleteOptions;
import io.kubernetes.client.util.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
@Slf4j
public class OperatorService {


    public void createService(CustomResource customResource) throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CustomObjectsApi api = new CustomObjectsApi(client);
        client.setDebugging(true);

        HashMap<String, Object> body = new HashMap<>();
        body.put("apiVersion", customResource.getApiVersion());
        body.put("kind", customResource.getKind());
        body.put("metadata", customResource.getMetadata());
        body.put("spec", customResource.getSpec());
        try {
            String[] groupVersionArr = customResource.getApiVersion().split("/");
            log.info(groupVersionArr[0], " ", groupVersionArr[1]);
            api.createNamespacedCustomObject(
                    groupVersionArr[0],
                    groupVersionArr[1],
                    customResource.getMetadata().get("namespace").toString(),
                    customResource.getPlural(),
                    body,
                    "true");
        } catch (ApiException e) {
            log.error("Error while deleting custom resource instance", e);
        }

    }

    public void deleteService(CustomResource customResource) throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CustomObjectsApi api = new CustomObjectsApi(client);
        client.setDebugging(true);

        try {
            V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();
            String[] groupVersionArr = customResource.getApiVersion().split("/");
            log.info(groupVersionArr[0], " ", groupVersionArr[1]);
            log.info("customResource: " + customResource.toString());
            api.deleteNamespacedCustomObject(
                    groupVersionArr[0],
                    groupVersionArr[1],
                    customResource.getMetadata().get("namespace").toString(),
                    customResource.getPlural(),
                    customResource.getMetadata().get("name").toString(),
                    v1DeleteOptions,
                    10,
                    true,
                    "propagationPolicy");
        } catch (ApiException e) {
            log.error("Error while deleting custom resource instance", e);
        }

    }

    public void bindService(HashMap<String, Object> body) throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CustomObjectsApi api = new CustomObjectsApi(client);
        client.setDebugging(true);

        try {
            api.createNamespacedCustomObject(
                    "binding.operators.coreos.com",
                    "v1alpha1",
                    "default",
                    "servicebindings",
                    body,
                    "true");
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
