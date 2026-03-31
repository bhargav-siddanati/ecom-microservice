package com.kafka.producer.config;

import com.kafka.producer.dto.RiderLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

/**
 * This class defines a spring cloud stream supplier which is used to work serverless and also to work with spring cloud stream. The Spring will scan the class and will create a bean for the supplier defined in the class. The supplier is defined as a bean and it is a simple supplier that returns a rider location object as output. This supplier can be used in spring cloud stream to produce messages to the Kafka topic. The supplier can be used in the application.properties file to define the output bindings for the supplier. The supplier can also be used in the application.yml file to define the output bindings for the supplier. The supplier can be used in the application.properties file to define the output bindings for the supplier. The supplier can also be used in the application.yml file to define the output bindings for the supplier.
 */
@Configuration
public class ProducerCloudStreams {
    @Bean
    public Supplier<RiderLocation> sendRiderLocation(){
        return () -> {
            RiderLocation loaction = new RiderLocation(103, 12.234, 15.678);
            System.out.println("Sending rider location: " + loaction);
            return loaction;
        };
    }
}
