package com.rsww.mikolekn.user_ms;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.loginTopic}")
    private String loginTopic;

    @Value("${spring.rabbitmq.queue.loginRequestQueue}")
    private String loginRequestQueue;

    @Value("${spring.rabbitmq.queue.loginResponseQueue}")
    private String loginResponseQueue;

    @Value("${spring.rabbitmq.queue.loginRequestRoutingKey}")
    private String loginRequestRoutingKey;

    @Value("${spring.rabbitmq.queue.loginResponseRoutingKey}")
    private String loginResponseRoutingKey;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Bean
    public TopicExchange loginTopic() {
        return new TopicExchange(loginTopic);
    }

    @Bean
    public Queue loginRequestQueue() {
        return new Queue(loginRequestQueue, true);
    }

    @Bean
    public Queue loginResponseQueue() {
        return new Queue(loginResponseQueue, true);
    }

    @Bean
    public Binding loginRequesstBinding(Queue loginRequestQueue, TopicExchange loginTopic) {
        return BindingBuilder.bind(loginRequestQueue).to(loginTopic).with(loginRequestRoutingKey);
    }

    @Bean
    public Binding loginResponseBinding(Queue loginResponseQueue, TopicExchange loginTopic) {
        return BindingBuilder.bind(loginResponseQueue).to(loginTopic).with(loginResponseRoutingKey);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setPort(Integer.parseInt(port));
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
}
