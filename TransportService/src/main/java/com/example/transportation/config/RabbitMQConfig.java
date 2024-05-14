package com.example.transportation.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    static final String transportTopic = "transport-topic";

    @Value("${spring.rabbitmq.queue.addFlightQueue}")
    private String addFlightQueue;

    @Value("${spring.rabbitmq.queue.updateFlightPriceQueue}")
    private String updateFlightPriceQueue;

    @Value("${spring.rabbitmq.queue.addFlightDataStore}")
    private String addFlightDataStore;

    @Value("${spring.rabbitmq.queue.reserveFlightQueue}")
    private String reserveFlightQueue;

    @Value("${spring.rabbitmq.queue.findFlightQueue}")
    private String findFlightQueue;

    @Value("${spring.rabbitmq.queue.cancelFlightQueue")
    private String cancelFlightQueue;

    @Bean
    public Queue addFlightQueue() {
        return new Queue(addFlightQueue, true);
    }

    @Bean
    public Queue cancelFlightQueue() {
        return new Queue(cancelFlightQueue, true);
    }

    @Bean
    public Queue updateFlightPriceQueue() {
        return new Queue(updateFlightPriceQueue, true);
    }

    @Bean
    public Queue findFlightQueue() {
        return new Queue(findFlightQueue, true);
    }

    @Bean
    public Queue addFlightDataStore() {
        return new Queue(addFlightDataStore, true);
    }

    @Bean
    public Queue reserveFlightQueue() {
        return new Queue(reserveFlightQueue, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(transportTopic);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
