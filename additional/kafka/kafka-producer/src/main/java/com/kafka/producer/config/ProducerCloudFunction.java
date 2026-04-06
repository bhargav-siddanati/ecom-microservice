package com.kafka.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * This class defines a spring cloud function which is used to work serverless and also to work with spring cloud stream. The Spring will scan the class and will create a bean for the function defined in the class. The function is defined as a bean and it is a simple function that takes a string as input and returns the uppercase version of the string as output. This function can be used in spring cloud stream to process the messages from the Kafka topic. The function can be used in the application.properties file to define the input and output bindings for the function. The function can also be used in the application.yml file to define the input and output bindings for the function. The function can be used in the application.properties file to define the input and output bindings for the function. The function can also be used in the application.yml file to define the input and output bindings for the function.
 */
@Configuration
public class ProducerCloudFunction {
    @Bean
    public Function<String, String> upperCase(){
        return String::toUpperCase; //value -> value.toUpperCase();
    }
}
