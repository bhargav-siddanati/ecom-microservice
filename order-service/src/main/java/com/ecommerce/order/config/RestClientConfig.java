package com.ecommerce.order.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
  @Autowired(required = false)
  private ObservationRegistry observer;

  @Autowired(required = false)
  private Tracer tracer;

  @Autowired private Propagator propagator;

  @Bean
  @LoadBalanced
  public RestClient.Builder getRestBuilderLb() {
    RestClient.Builder builder = RestClient.builder();
    if (observer != null) {
      builder.requestInterceptor(createTracingInterceptor());
    }
    return builder;
  }

  private ClientHttpRequestInterceptor createTracingInterceptor() {
    return ((request, body, execution) -> {
      if (tracer != null && propagator != null && tracer.currentSpan() != null) {
        propagator.inject(
            tracer.currentTraceContext().context(),
            request.getHeaders(),
            (carrier, key, value) -> carrier.add(key, value));
      }
      return execution.execute(request, body);
    });
  }

  @Bean
  @Primary
  public RestClient.Builder getRestBuilder() {
    return RestClient.builder();
  }
}
