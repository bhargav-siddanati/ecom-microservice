package com.ecommerce.order.config;

import com.ecommerce.order.client.UserHttpClientExchangeProvider;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class UserHttpClientServiceConfig {
    @Bean
    public UserHttpClientExchangeProvider getUserHttpClientExchange(
            @LoadBalanced RestClient.Builder builder){
        var restClient = builder.baseUrl("http://user-service")
                                        .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                                                ((req, res) -> Optional.empty()))
                                        .build();
        var adapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserHttpClientExchangeProvider.class);
    }
}
