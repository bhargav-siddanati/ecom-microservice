package com.inter.communication.consumer.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-restclient")
@RequiredArgsConstructor
public class RestClientController {
  private final RestClientProvider provider;

  @GetMapping("/instance-info")
  public String getInstance() {
    return provider.getInstanceInfo();
  }
}
