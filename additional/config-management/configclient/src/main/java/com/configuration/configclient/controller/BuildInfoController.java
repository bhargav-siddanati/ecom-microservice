package com.configuration.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildInfoController {

    @Value("${build.id}")
    private String buildId;

    @Value("${build.version}")
    private String buildVersion;

    @Value("${build.name}")
    private String buildName;

    @GetMapping("/buildInfo")
    public String getBuildInfo(){
        return "Build ID: " + buildId + ", Build Version: " + buildVersion
                + ", Build Name: " + buildName;
    }
}
