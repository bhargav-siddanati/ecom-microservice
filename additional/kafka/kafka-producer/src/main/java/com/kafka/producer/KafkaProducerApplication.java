package com.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);

		// working code here
		/*SpringApplication app = new SpringApplication(KafkaProducerApplication.class);
		Map<String, Object> props = new HashMap<>();

		props.put("spring.cloud.function.definition", "sendRiderLocation");
		props.put("spring.cloud.stream.function.definition", "sendRiderLocation");
        // Binding Configurations (Destination and Content-Type)
        props.put("spring.cloud.stream.bindings.sendRiderLocation-out-0.destination", "my-topic");
        props.put("spring.cloud.stream.bindings.sendRiderLocation-out-0.content-type", "application/json");
		props.put("spring.cloud.stream.poller.fixed-delay", "5000");
        // Kafka Binder Configurations (Broker Address)
        props.put("spring.cloud.stream.kafka.binder.brokers", "localhost:9092");

        app.setDefaultProperties(props);
        app.run(args);*/
	}

}
