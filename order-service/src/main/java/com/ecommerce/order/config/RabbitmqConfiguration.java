package com.ecommerce.order.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiguration {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public Queue queue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public TopicExchange exchange(){
        return ExchangeBuilder.topicExchange(exchangeName)
                              .durable(true)
                              .build();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                                .to(exchange())
                                .with(routingKey);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    // Provide a MessageConverter that uses the application's ObjectMapper (preserves global Jackson config)
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
    //        return new ObjectMapperBasedMessageConverter(objectMapper);
    return new JacksonJsonMessageConverter();
    }

    // Ensure RabbitTemplate uses the same converter for producers
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setExchange(exchangeName);
        return template;
    }

    // Ensure listeners use the same converter
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    // Small ObjectMapper-based MessageConverter to replace deprecated helper converter usage.
    private static final class ObjectMapperBasedMessageConverter implements MessageConverter {
        private static final String TYPE_HEADER = "__TypeId__";
        private final ObjectMapper objectMapper;

        ObjectMapperBasedMessageConverter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
            try {
                byte[] body = objectMapper.writeValueAsBytes(object);
                messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
                messageProperties.setContentLength(body.length);
                messageProperties.setHeader(TYPE_HEADER, object.getClass().getName());
                 return new Message(body, messageProperties);
            } catch (JsonProcessingException e) {
                throw new MessageConversionException("Failed to convert object to JSON", e);
            }
        }

        @Override
        public Object fromMessage(Message message) throws MessageConversionException {
            try {
                byte[] body = message.getBody();
                MessageProperties props = message.getMessageProperties();
                Map<String, Object> headers = props.getHeaders();
                if (headers != null && headers.containsKey(TYPE_HEADER)) {
                    Object typeId = headers.get(TYPE_HEADER);
                    if (typeId instanceof String) {
                        try {
                            Class<?> clazz = Class.forName((String) typeId);
                            return objectMapper.readValue(body, clazz);
                        } catch (ClassNotFoundException ignored) {
                            // fallback to generic Object below
                        }
                    }
                }
                // fallback: deserialize to generic Object (Map/List) using application's ObjectMapper
                return objectMapper.readValue(body, Object.class);
            } catch (Exception e) {
                throw new MessageConversionException("Failed to convert JSON to object", e);
            }
        }
    }
}
