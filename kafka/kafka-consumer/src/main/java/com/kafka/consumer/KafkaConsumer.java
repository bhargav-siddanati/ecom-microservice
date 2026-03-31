package com.kafka.consumer;

import com.kafka.consumer.dto.RiderLocation;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    //@KafkaListener(topics = "my-topic", groupId = "my-new-group")
    public void listener(RiderLocation message){
        System.out.println("Received message from Kafka topic is : " + message.toString());
    }

    // Listening messages from the same topic with different group id
    /*@KafkaListener(topics = "my-topic", groupId = "my-new-group-1")
    public void listener2(String message){
        System.out.println("Received message from Kafka topic is : " + message);
    }*/
}
