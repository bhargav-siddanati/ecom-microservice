package com.ecom.notification;

import com.ecom.notification.payload.OrderCreatedEvent;
import com.ecom.notification.payload.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderEventConsumer {
    // Working with the map, when sending the notification data as a map.
    /*@RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(Map<String, Object> orderEvent){
        System.out.println("Received order event: " + orderEvent);
        Long orderId = Long.parseLong(orderEvent.get("orderId").toString());
        String orderStatus = orderEvent.get("status").toString();
        System.out.println("Order ID: " + orderId + ", Status: " + orderStatus);
    }*/

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(OrderCreatedEvent orderEvent){
        System.out.println("Received order event: " + orderEvent);
        Long orderId = orderEvent.getOrderId();
        OrderStatus orderStatus = orderEvent.getStatus();
        System.out.println("Order ID: " + orderId + ", Status: " + orderStatus);
    }
}
