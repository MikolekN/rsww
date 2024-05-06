package com.rsww.mikolekn.APIGateway.login;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginRabbitMqConfig {
    @Value("${spring.rabbitmq.queue.loginQueue}")
    private String loginQueue;

    @Bean
    public Queue loginQueue() {
        return new Queue(loginQueue);
    }
}
