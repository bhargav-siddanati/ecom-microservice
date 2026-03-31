package com.kafka.consumer.config;

import com.kafka.consumer.dto.RiderLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumerStream {
    @Bean
    public Consumer<RiderLocation> processRiderLocation(){
        return location -> System.out.println("Received Rider Location: " + location);
    }
}
