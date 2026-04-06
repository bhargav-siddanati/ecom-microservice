package com.ecom.notification;

import com.ecom.notification.payload.OrderCreatedEvent;
import com.ecom.notification.payload.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Service
@Slf4j
public class OrderEventConsumer {
    // Working with the map, when sending the notification data as a map.
    /*@RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(Map<String, Object> orderEvent){
        System.out.println("Received order event: " + orderEvent);
        Long orderId = Long.parseLong(orderEvent.get("orderId").toString());
        String orderStatus = orderEvent.get("status").toString();
        System.out.println("Order ID: " + orderId + ", Status: " + orderStatus);
    }*/
    // working with the actual event class, when sending the notification data as an object.
    /*@RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(OrderCreatedEvent orderEvent){
        System.out.println("Received order event: " + orderEvent);
        Long orderId = orderEvent.getOrderId();
        OrderStatus orderStatus = orderEvent.getStatus();
        System.out.println("Order ID: " + orderId + ", Status: " + orderStatus);
    }*/

    // Using Spring Cloud Stream functional programming model to consume messages, instead of using @RabbitListener.
    @Bean
    public Consumer<OrderCreatedEvent> orderCreated(){
        return event -> {
            log.info("Received Order event : {}", event);
        };
    }
}
