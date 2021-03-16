package com.example.proxy.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
@ToString
public class CustomResource {

    private String apiVersion;
    private String kind;
    private Map<String, Object> metadata;
    private Map<String, Object> spec;
    private String plural;

}
