package com.example.transportation.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.addFlightQueue}")
    private String addFlightQueue;

    @Value("${spring.rabbitmq.queue.updateFlightPriceQueue}")
    private String updateFlightPriceQueue;

    @Value("${spring.rabbitmq.queue.addFlightDataStore}")
    private String addFlightDataStore;

    @Value("${spring.rabbitmq.queue.updateFlightQueue}")
    private String updateFlightQueue;

    @Value("${spring.rabbitmq.queue.reserveFlightQueue}")
    private String reserveFlightQueue;

    @Value("${spring.rabbitmq.queue.cancelFlightReservationQueue}")
    private String cancelFlightReservationQueue;

    @Value("${spring.rabbitmq.queue.confirmFlightReservationQueue}")
    private String confirmFlightReservationQueue;

    @Bean
    public Queue addFlightQueue() {
        return new Queue(addFlightQueue, true);
    }

    @Bean
    public Queue updateFlightPriceQueue() {
        return new Queue(updateFlightPriceQueue, true);
    }

    @Bean
    public Queue addFlightDataStore() {
        return new Queue(addFlightDataStore, true);
    }

    @Bean
    public Queue updateFlightQueue() {
        return new Queue(updateFlightQueue, true);
    }

    @Bean
    public Queue reserveFlightQueue() {
        return new Queue(reserveFlightQueue, true);
    }

    @Bean
    public Queue cancelFlightReservationQueue() {
        return new Queue(cancelFlightReservationQueue, true);
    }

    @Bean
    public Queue confirmFlightReservationQueue() {
        return new Queue(confirmFlightReservationQueue, true);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
