package com.ecommerce.order.client;

import com.ecommerce.order.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserHttpClientExchangeProvider {
    @GetExchange("/api/user/{id}")
    UserResponse getUserById(@PathVariable String id);
}
