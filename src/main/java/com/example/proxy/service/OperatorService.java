package com.example.proxy.service;

import java.io.IOException;
import java.util.HashMap;
import org.springframework.stereotype.Service;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CustomObjectsApi;
// import io.kubernetes.client.openapi.ApiClient;
// import io.kubernetes.client.openapi.ApiException;
// import io.kubernetes.client.openapi.Configuration;
// import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.util.Config;

@Service
public class OperatorService {


    public void createService() throws IOException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CustomObjectsApi api = new CustomObjectsApi(client);
        client.setDebugging(true);

        HashMap<String, Object> body = new HashMap<>();
        body.put("apiVersion", "sample-operator.example.com/v1");
        body.put("kind", "SampleOperator");

        HashMap<String, Object> metadata = new HashMap<>();
        metadata.put("name", "sampleoperator-sample");
        metadata.put("namespace", "default");

        body.put("metadata", metadata);

        HashMap<String, Object> spec  = new HashMap<>();
        spec.put("serviceInstanceName", "sample-instance1");
        body.put("spec", spec);
        try {
            api.createNamespacedCustomObject("sample-operator.example.com", "v1", "default", "sampleoperators", body, "true");
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
