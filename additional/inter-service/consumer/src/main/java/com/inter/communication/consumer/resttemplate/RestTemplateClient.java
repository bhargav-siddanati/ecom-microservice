package com.inter.communication.consumer.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestTemplateClient {
  private final RestTemplate restTemplate;
  private final String INSTANCE_BASE_URL = "http://localhost:8081";

  public String getInstanceInfo() {
    return restTemplate.getForObject(INSTANCE_BASE_URL + "/instance-info", String.class);
  }
}
