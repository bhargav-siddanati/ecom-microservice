/*
package com.kafka.producer.config;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerStreamConfig implements EnvironmentPostProcessor {

    public KafkaProducerStreamConfig(){}
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> customProps = new HashMap<>();
        customProps.put("spring.cloud.function.definition", "sendRiderLocation");
        customProps.put("spring.cloud.stream.function.definition", "sendRiderLocation");
        customProps.put("spring.cloud.stream.bindings.sendRiderLocation-out-0.destination", "my-topic");
        customProps.put("spring.cloud.stream.bindings.sendRiderLocation-out-0.content-type", "application/json");
        customProps.put("spring.cloud.stream.poller.fixed-delay", 10000);

//        String brokers = environment.getProperty("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092");
//        customProps.put("spring.cloud.stream.kafka.binder.brokers", brokers);
        customProps.put("spring.cloud.stream.kafka.binder.brokers", "localhost:9092");

        environment.getPropertySources().addFirst(new MapPropertySource("enterpriseProps", customProps));

        */
/*customProps.put("spring.cloud.stream.bindings.processRiderLocation-in-0.group", "rider-service-group");*//*


    }
}
*/
package com.kafka.producer.config;

// Use the new package for Spring 4.0+
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import java.util.HashMap;
import java.util.Map;

public class KafkaProducerStreamConfig {/*implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> props = new HashMap<>();

        // Use 'spring.cloud.function.definition' as the standard property name
        props.put("spring.cloud.function.definition", "sendRiderLocation");
        props.put("spring.cloud.stream.bindings.sendRiderLocation-out-0.destination", "my-topic");
        props.put("spring.cloud.stream.kafka.binder.brokers", "localhost:9092");
// CRITICAL: This triggers the Supplier every 5 seconds
        props.put("spring.cloud.stream.poller.fixed-delay", "5000");

        // OPTIONAL: Ensure JSON is handled correctly
        props.put("spring.cloud.stream.bindings.sendRiderLocation-out-0.content-type", "application/json");
        // High precedence injection
        environment.getPropertySources().addFirst(new MapPropertySource("productionConfigs", props));
    }*/
}