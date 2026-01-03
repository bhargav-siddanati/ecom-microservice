package com.inter.communication.consumer.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name = "consumer-service", url = "${producer.service.url}")
public interface OpenFeignClient {
  @GetMapping("/instance-info")
  String getInstanceInfo();
}
