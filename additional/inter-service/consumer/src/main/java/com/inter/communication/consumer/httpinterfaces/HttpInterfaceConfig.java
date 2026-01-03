package com.inter.communication.consumer.httpinterfaces;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {
    @Value("${producer.service.url}")
    private String baseUrl;
    /*
    Follow this page to work with RestClient and RestTemplate
    https://docs.spring.io/spring-framework/reference/6.0/integration/rest-clients.html#rest-http-interface
     */
    @Bean
    public HttpInterfaceProvider webClientHttpInterfaceProvider(){
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        WebClientAdapter adapter = WebClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(HttpInterfaceProvider.class);
    }
}
