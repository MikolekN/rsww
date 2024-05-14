package com.rsww.mikolekn.APIGateway.country;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountryRabbitMqConfig {
    @Value("${spring.rabbitmq.queue.countryQueue}")
    private String countryQueue;

    @Bean
    public Queue countryQueue() {
        return new Queue(countryQueue);
    }
}
