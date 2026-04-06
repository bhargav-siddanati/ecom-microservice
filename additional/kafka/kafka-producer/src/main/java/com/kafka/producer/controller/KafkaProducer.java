package com.kafka.producer.controller;

import com.kafka.producer.dto.RiderLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KafkaProducer {

    @Autowired
    private final KafkaTemplate<String, RiderLocation> kafkaTemplate;

   /* @PostMapping("/send")
    public String sendMessage(@RequestParam String message){
        kafkaTemplate.send("my-topic", message);
        return "Message sent to Kafka topic is : " + message;
    }*/

    @PostMapping("/serialize")
    public String serializeObject(){
        RiderLocation location = new RiderLocation(1, 40.531, 13.123);
        kafkaTemplate.send("my-topic", location);
        return "Message sent to kafka : " + location.toString();
    }
}
