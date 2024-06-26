package com.rsww.mikolekn.APIGateway.order.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderRabbitMqConfig {
    @Value("${spring.rabbitmq.queue.reserveTrip}")
    private String reserveTrip;
    @Value("${spring.rabbitmq.queue.ordersQueue}")
    private String ordersQueue;
    @Value("${spring.rabbitmq.queue.orderInfoQueue}")
    private String orderInfoQueue;

    @Bean
    public Queue reserveTrip() {
        return new Queue(reserveTrip);
    }

    @Bean
    public Queue ordersQueue() {
        return new Queue(ordersQueue);
    }

    @Bean
    public Queue orderInfoQueue() {
        return new Queue(orderInfoQueue);
    }
}
