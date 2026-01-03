package com.inter.communication.producer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProviderController {

    @Value("${server.port}")
    private String port;

    private final String INSTANCE_ID = UUID.randomUUID().toString();

    @GetMapping("/instance-info")
    public String getInstace(){
        System.out.println("Provider Instance ID: " + INSTANCE_ID + ", Port : " + port);
        return "Provider Instance ID: " + INSTANCE_ID + ", Port : "
                + port;
    }
}
