package com.inter.communication.consumer.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestTemplateClient {
  private final RestTemplate restTemplate;
//  private final String INSTANCE_BASE_URL = "http://localhost:8081";

  @Value("${producer.service.url}")
  private String INSTANCE_BASE_URL;

  public String getInstanceInfo() {
    return restTemplate.getForObject(INSTANCE_BASE_URL + "/instance-info", String.class);
  }
}
