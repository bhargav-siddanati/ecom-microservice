package com.inter.communication.consumer.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-restTemplate")
@RequiredArgsConstructor
public class RestTemplateController {

  private final RestTemplateClient client;

  @GetMapping("/instance-info")
  public String getInstance() {
    return client.getInstanceInfo();
  }
}
