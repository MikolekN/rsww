package com.rsww.mikolekn.APIGateway.offer;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OffersRabbitMqConfig {

    @Value("${spring.rabbitmq.queue.offersQueue}")
    private String offersQueue;
    @Value("${spring.rabbitmq.queue.offerQueue}")
    private String offerQueue;
    @Bean
    public Queue offersQueue() {
        return new Queue(offersQueue);
    }

    @Bean
    public Queue offerQueue() {
        return new Queue(offerQueue);
    }
}
