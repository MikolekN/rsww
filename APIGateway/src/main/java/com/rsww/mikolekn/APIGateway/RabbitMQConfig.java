package com.rsww.mikolekn.APIGateway;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.loginRequestQueue}")
    private String loginRequestQueue;

    @Value("${spring.rabbitmq.queue.loginResponseQueue}")
    private String loginResponseQueue;

    @Bean
    public Queue loginRequestQueue() {
        return new Queue(loginRequestQueue);
    }

    @Bean
    public Queue loginResponseQueue() {
        return new Queue(loginResponseQueue);
    }
}