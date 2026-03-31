package com.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication {

	public static void main(String[] args) {
		// SpringApplication app =
		SpringApplication.run(KafkaProducerApplication.class, args);
		/*
		Map<String, Object> props = new HashMap<>();
        // Application Name
        props.put("spring.application.name", "kafka-producer");

        // Binding Configurations (Destination and Content-Type)
        props.put("spring.cloud.stream.bindings.sendRiderLocation-out-0.destination", "my-topic");
        props.put("spring.cloud.stream.bindings.sendRiderLocation-out-0.content-type", "application/json");

        // Kafka Binder Configurations (Broker Address)
        props.put("spring.cloud.stream.kafka.binder.brokers", "localhost:9092");

        app.setDefaultProperties(props);
        app.run(args);
		 */
	}

}
