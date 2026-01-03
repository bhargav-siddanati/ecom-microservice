package com.inter.communication.consumer.httpinterfaces;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface HttpInterfaceProvider {
    @GetExchange("/instance-info")
    String getInstanceInfo();
}
