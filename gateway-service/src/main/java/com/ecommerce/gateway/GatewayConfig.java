package com.ecommerce.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator createRoutes(RouteLocatorBuilder builder){
        /*
        If you dont want to expose the full path to the outer world then use the url rewrite path filter.
        i.e, Previous: http://localhost:8080/api/product
             Now: http://localhost:8080/product
        Before that remove the .path("/product/**")
        just append the '.filters( f -> f.rewritePath("/product(?<segment>?.*)","/api/product${segment}"))'
         */
        return builder.routes()
                .route("user-service", r -> r.path("/api/user/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("gateService")))
                        .uri("lb://USER-SERVICE"))
                .route("order-service", r -> r.path("/api/order/**","/api/cart/**")
                        .uri("lb://ORDER-SERVICE"))
                .route("product-service", r -> r.path("/api/product/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .route("eureka-service", r -> r.path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main","/"))
                        .uri("http://localhost:8761"))
                .route("eureka-server-static", r -> r.path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}
