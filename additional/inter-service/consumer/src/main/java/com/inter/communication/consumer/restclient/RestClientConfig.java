package com.inter.communication.consumer.restclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${producer.service.url}")
    private String baseUrl;

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @LoadBalanced
    @Primary
    public RestClient getRestClient( RestClient.Builder builder){
        return builder.baseUrl("http://producer")
                .build();
    }
}
