package com.rsww.mikolekn.APIGateway.order;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderRabbitMqConfig {
    @Value("${spring.rabbitmq.queue.reserveTrip}")
    private String reserveTrip;

    @Bean
    public Queue reserveTrip() {
        return new Queue(reserveTrip);
    }
}
