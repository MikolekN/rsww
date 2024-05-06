package com.rsww.lydka.paymentservice.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.queue.paymentExchange}")
    private String paymentExchange;

    @Value("${spring.rabbitmq.queue.requestPaymentQueue}")
    private String reqeustPaymentQueue;

    @Value("${spring.rabbitmq.queue.responsePaymentQueue}")
    private String responsePaymentQueue;

    @Value("${spring.rabbitmq.queue.paymentRequestRoutingKey}")
    private String paymentRequestRoutingKey;

    @Value("${spring.rabbitmq.queue.paymentResponseRoutingKey}")
    private String paymentResponseRoutingKey;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange("paymentExchange");
    }

    @Bean
    public Queue requestPaymentQueue() {
        return new Queue(reqeustPaymentQueue, true);
    }

    @Bean
    public Queue responsePaymentQueue() {
        return new Queue(responsePaymentQueue, true);
    }

    @Bean
    public Binding declareRequestPaymentBinding() {
        return BindingBuilder.bind(requestPaymentQueue()).to(paymentExchange()).with(paymentRequestRoutingKey);
    }

    @Bean
    public Binding declareResponsePaymentBinding() {
        return BindingBuilder.bind(responsePaymentQueue()).to(paymentExchange()).with(paymentResponseRoutingKey);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setPort(Integer.parseInt(port));
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
