package com.rsww.mikolekn.APIGateway;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.loginTopic}")
    private String loginTopic;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(loginTopic);
    }
}