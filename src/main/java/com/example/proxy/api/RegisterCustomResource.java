package com.example.proxy.api;

import com.example.proxy.model.CustomResource;
import com.example.proxy.service.CustomResourceCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class RegisterCustomResource {

    @Autowired
    private CustomResourceCache customResourceCache;

    @PostMapping(value = "/api/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Operator registration in ServiceFabric")
    public ResponseEntity registerOperator(@RequestParam(value = "planid", required = true) String planId,
                                                 @RequestParam(value = "apiversion", required = true) String apiVersion,
                                                 @RequestParam(value = "kind", required = true) String kind,
                                                 @RequestParam(value = "plural", required = true) String plural){

        CustomResource crSampleOperator = new CustomResource();
        crSampleOperator.setApiVersion(apiVersion);
        crSampleOperator.setKind(kind);
        crSampleOperator.setPlural(plural);
        customResourceCache.add(planId, crSampleOperator);
        return ResponseEntity.ok().build();
    }
}
