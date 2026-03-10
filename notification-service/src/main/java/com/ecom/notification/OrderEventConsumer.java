package com.ecom.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderEventConsumer {
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(Map<String, Object> orderEvent){
        System.out.println("Received order event: " + orderEvent);
        Long orderId = Long.parseLong(orderEvent.get("orderId").toString());
        String orderStatus = orderEvent.get("status").toString();
        System.out.println("Order ID: " + orderId + ", Status: " + orderStatus);
    }
}
