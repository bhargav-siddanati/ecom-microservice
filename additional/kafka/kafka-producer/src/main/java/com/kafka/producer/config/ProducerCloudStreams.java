package com.kafka.producer.config;

import com.kafka.producer.dto.RiderLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class defines a spring cloud stream supplier which is used to work serverless and also to work with spring cloud stream. The Spring will scan the class and will create a bean for the supplier defined in the class. The supplier is defined as a bean and it is a simple supplier that returns a rider location object as output. This supplier can be used in spring cloud stream to produce messages to the Kafka topic. The supplier can be used in the application.properties file to define the output bindings for the supplier. The supplier can also be used in the application.yml file to define the output bindings for the supplier. The supplier can be used in the application.properties file to define the output bindings for the supplier. The supplier can also be used in the application.yml file to define the output bindings for the supplier.
 */
@Configuration
public class ProducerCloudStreams {
    @Bean
    public Supplier<RiderLocation> sendRiderLocation(){
        Random random = new Random();
        return () -> {
            int id = random.nextInt(20);
            RiderLocation location = new RiderLocation(id, 12.234, 15.678);
            System.out.println("Sending rider location: " + location);
            return location;
        };
    }
    @Bean
    public Supplier<Message<String>> sendRiderStatus(){
        Random random = new Random();
        return () -> {
            int id = random.nextInt(20);
            String status = random.nextBoolean()?"Ride Started":"Ride Ended";
            System.out.println("Sending rider status: " + status);
            return MessageBuilder.withPayload("Rider: " + id + " - " + status)
                    .setHeader(KafkaHeaders.KEY, String.valueOf(id).getBytes())
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
                    .build();

        };
    }
    /*@Bean
    public Function<RiderLocation, Message<RiderLocation>> processLocation() {
        return location -> {
            System.out.println("Processing Location for: " + location.id());

            // Adding a custom header for production tracking
            return MessageBuilder.withPayload(location)
                    .setHeader("processed-at", System.currentTimeMillis())
                    .build();
        };
    }*/
}
