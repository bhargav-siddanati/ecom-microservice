package com.configuration.configclient.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="build")
@Data
public class BuildInfo {
    // Use the same name as in the properties file
    private String id;
    private String version;
    private String name;
}
