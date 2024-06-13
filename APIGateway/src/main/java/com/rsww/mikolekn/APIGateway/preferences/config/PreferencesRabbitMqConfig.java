package com.rsww.mikolekn.APIGateway.preferences.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreferencesRabbitMqConfig {
    @Value("${spring.rabbitmq.queue.getPreferences}")
    private String getPreferences;

    @Bean
    public Queue getPreferences() {
        return new Queue(getPreferences);
    }

    @Value("${spring.rabbitmq.queue.PreferencesFrontQueue}")
    private String preferencesFrontQueue;

    @Bean
    public Queue preferencesFrontQueue() {
        return new Queue(preferencesFrontQueue);
    }
}
