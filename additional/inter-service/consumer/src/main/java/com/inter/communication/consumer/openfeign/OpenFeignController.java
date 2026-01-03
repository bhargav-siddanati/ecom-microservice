package com.inter.communication.consumer.openfeign;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-openfeign")
@RequiredArgsConstructor
public class OpenFeignController {
  private final OpenFeignClient client;

  @GetMapping("/instance-info")
  public String getInstance() {
    return client.getInstanceInfo();
  }
}
