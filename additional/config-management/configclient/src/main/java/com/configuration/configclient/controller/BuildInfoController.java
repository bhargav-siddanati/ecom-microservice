package com.configuration.configclient.controller;

import com.configuration.configclient.configurations.BuildInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BuildInfoController {
//    private BuildInfo buildInfo;
    /* Unfortunately, the profiles active properties is not set or comment out the properties
       in the application.yaml file then there are no default properties are found for build
       then the application will failed to start.

       To avoid this issue, you can set default values for the properties using the syntax:
       @Value("${property.name:default_value}")

     */
    @Value("${build.id:000}")
    private String buildId;

    @Value("${build.version:0.0.0}")
    private String buildVersion;

    @Value("${build.name:default-build}")
    private String buildName;

    @GetMapping("/buildInfo")
    public String getBuildInfo(){
        return "Build ID: " + buildId + ", Build Version: " + buildVersion
                + ", Build Name: " + buildName;
    }

    /*@GetMapping("/buildInfo")
    public String getBuildInfo(){
        return "Build ID: " + buildInfo.getId() + ", Build Version: "
                + buildInfo.getVersion()
                + ", Build Name: " + buildInfo.getName();
    }*/
}
