package com.rsww.mikolekn.UserService;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.loginTopic}")
    private String loginTopic;

    @Value("${spring.rabbitmq.queue.loginQueue}")
    private String loginQueue;

    @Bean
    public Queue loginRequestQueue() {
        return new Queue(loginQueue);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(loginTopic);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("request");
    }
}
