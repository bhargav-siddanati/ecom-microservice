package com.ecommerce.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

  @Bean
  public RedisRateLimiter redisRateLimiter() {
    return new RedisRateLimiter(1, 1, 1);
  }

  @Bean
  public KeyResolver hostNameKeyResolver() {
    return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
  }

  @Bean
  public RouteLocator createRoutes(RouteLocatorBuilder builder) {
    /*
    If you dont want to expose the full path to the outer world then use the url rewrite path filter.
    i.e, Previous: http://localhost:8080/api/product
         Now: http://localhost:8080/product
    Before that remove the .path("/product/**")
    just append the '.filters( f -> f.rewritePath("/product(?<segment>?.*)","/api/product${segment}"))'
     */
    return builder
        .routes()
        .route(
            "user-service",
            r ->
                r.path("/api/user/**")
                    .filters(
                        f ->
                            f.retry(
                                    retryConfig ->
                                        retryConfig.setRetries(2).setMethods(HttpMethod.GET))
                                .requestRateLimiter(
                                    conf ->
                                        conf.setRateLimiter(redisRateLimiter())
                                            .setKeyResolver(hostNameKeyResolver()))
                                .circuitBreaker(
                                    config ->
                                        config
                                            .setName("gateService")
                                            .setFallbackUri("forward:/fallback/user")))
                    .uri("lb://USER-SERVICE"))
        .route(
            "order-service", r -> r.path("/api/order/**", "/api/cart/**").uri("lb://ORDER-SERVICE"))
        .route(
            "product-service",
            r ->
                r.path("/api/product/**")
                    .filters(
                        f ->
                            f.retry(
                                    retryConfig ->
                                        retryConfig.setRetries(2).setMethods(HttpMethod.GET))
                                .requestRateLimiter(
                                    conf ->
                                        conf.setRateLimiter(redisRateLimiter())
                                            .setKeyResolver(hostNameKeyResolver()))
                                .circuitBreaker(
                                    config ->
                                        config
                                            .setName("gateService")
                                            .setFallbackUri("forward:/fallback/user")))
                    .uri("lb://PRODUCT-SERVICE"))
        .route(
            "eureka-service",
            r ->
                r.path("/eureka/main")
                    .filters(f -> f.rewritePath("/eureka/main", "/"))
                    .uri("http://localhost:8761"))
        .route("eureka-server-static", r -> r.path("/eureka/**").uri("http://localhost:8761"))
        .build();
  }
}
