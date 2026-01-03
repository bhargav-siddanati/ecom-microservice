package com.inter.communication.consumer.restclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${producer.service.url}")
    private String baseUrl;
    @Bean
    public RestClient getRestClient(RestClient.Builder builder){
        return builder.baseUrl(baseUrl)
                .build();
    }
}
