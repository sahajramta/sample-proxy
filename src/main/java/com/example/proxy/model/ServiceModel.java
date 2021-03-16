package com.example.proxy.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceModel {

    String group;
    String version;
    String kind;
    String resource;
    String name;
    String id;
}
