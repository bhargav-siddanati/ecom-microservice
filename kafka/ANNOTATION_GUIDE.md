# @Configuration vs @Component Annotations - Usage Guide

## Overview
Both `@Configuration` and `@Component` are Spring stereotypes used for Bean registration, but they serve different purposes and use cases.

---

## 1. @Component Annotation

### Purpose
- Marks a class as a Spring-managed component
- Auto-detects and registers beans through component scanning
- Used for general purpose business logic classes

### Where to Use
- **Service Classes**: Business logic that doesn't create other beans
- **Repository Classes**: Data access objects
- **Event Listeners**: Kafka listeners, message handlers
- **Utility Classes**: Helper components
- **Stereotype Annotations**: @Service, @Repository, @Controller are meta-annotations of @Component

### Example in Your Project
```java
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "my-topic", groupId = "my-new-group")
    public void listener(String message){
        System.out.println("Received message from Kafka topic is : " + message);
    }
}
```

### Real-World Example - Service Class
```java
@Component  // or @Service (which is a specialized @Component)
public class UserService {
    
    public void processUser(User user) {
        // Business logic
    }
}
```

---

## 2. @Configuration Annotation

### Purpose
- Indicates that a class declares @Bean-producing methods
- Used to create complex bean configurations
- Replaces XML configuration files
- Allows programmatic bean registration with custom logic

### Where to Use
- **Database Configuration**: DataSource, JdbcTemplate beans
- **Third-party Library Integration**: Creating beans for external libraries
- **Complex Bean Dependencies**: When beans need conditional creation or custom initialization
- **Security Configuration**: Authentication, authorization setup
- **Cache Configuration**: Cache managers, caching strategies
- **Custom Protocol Handlers**: REST templates, WebClient beans

### Example - Kafka Configuration
```java
@Configuration
public class KafkaConfig {
    
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
```

---

## 3. Key Differences

| Feature | @Component | @Configuration |
|---------|-----------|-----------------|
| Purpose | Mark business logic classes | Define bean creation/configuration |
| @Bean Methods | Not allowed | Required - declares bean factories |
| Use Cases | Services, Controllers, Listeners | Complex configurations, integrations |
| Bean Creation | Auto-instantiation | Custom bean factory methods |
| Dependencies | Injected via constructor/setters | Created explicitly in @Bean methods |
| Scope Control | Limited | Full control via @Bean methods |
| Proxying | Standard CGLIB proxy | Enhanced proxy with method interception |

---

## 4. Common Use Cases in Your Project

### A. Kafka Consumer - Use @Component ✓
```java
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "my-topic", groupId = "my-new-group")
    public void listener(String message){
        System.out.println("Received message: " + message);
    }
}
```
**Why?** Simple listener class with no bean creation needed

---

### B. Kafka Producer Service - Use @Component or @Service ✓
```java
@Service  // @Service is a specialized @Component
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
```
**Why?** Encapsulates producer business logic

---

### C. Kafka Infrastructure Configuration - Use @Configuration ✓
```java
@Configuration
public class KafkaInfrastructureConfig {
    
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        // Complex producer setup
        return new DefaultProducerFactory<>(configProps());
    }
    
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        // Complex consumer setup
        return new DefaultConsumerFactory<>(configProps());
    }
    
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
```
**Why?** Complex infrastructure setup with multiple bean creation logic

---

### D. REST Controller - Use @RestController ✓
```java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message){
        kafkaTemplate.send("my-topic", message);
        return "Message sent: " + message;
    }
}
```
**Why?** @RestController is a specialized @Component for HTTP endpoints

---

### E. Event Publisher - Use @Component ✓
```java
@Component
public class OrderEventPublisher {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    public void publishOrderCreated(Order order) {
        OrderEvent event = new OrderEvent(order);
        kafkaTemplate.send("order-topic", event);
    }
}
```
**Why?** Business logic class that publishes events

---

### F. Conditional Bean Configuration - Use @Configuration ✓
```java
@Configuration
public class CacheConfig {
    
    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
    
    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager defaultCacheManager() {
        return new NoOpCacheManager();
    }
}
```
**Why?** Conditional bean creation based on properties

---

## 5. Decision Tree

```
Do you need to create other beans using @Bean methods?
├─ YES → Use @Configuration
│        (Complex configurations, infrastructure setup, multiple bean definitions)
│
└─ NO → Use @Component or specialized stereotype
        ├─ Business Service → @Service
        ├─ Data Access → @Repository
        ├─ REST Endpoint → @RestController
        ├─ Event Listener → @Component
        └─ Generic Component → @Component
```

---

## 6. Best Practices

1. **Use Stereotypes**: Prefer @Service, @Repository, @Controller over @Component when applicable
2. **@Configuration for Infrastructure**: Keep infrastructure and configuration logic separate from business logic
3. **@Component for Business Logic**: Use for actual service implementations
4. **Separate Concerns**: Don't mix business logic with infrastructure configuration
5. **Constructor Injection**: Use dependency injection instead of manual bean creation
6. **Environment-Specific Config**: Use @Profile and @ConditionalOnProperty for different environments

---

## 7. Summary

- **@Component**: For business logic classes that will be auto-instantiated
- **@Configuration**: For infrastructure/configuration classes that produce multiple beans
- **In Your Kafka Project**:
  - Use `@Component` for KafkaConsumer listeners
  - Use `@Configuration` for Kafka producer/consumer factory configurations
  - Use `@Service` for business logic services that handle Kafka operations
  - Use `@RestController` for HTTP endpoints that trigger Kafka operations

