//package com.rsww.mikolekn.rsww_user;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//    @Value("${spring.rabbitmq.topic.logInTopic}")
//    private String logInTopic;
//
//    @Value("${spring.rabbitmq.queue.receiveLogInAttemptQueue}")
//    private String receiveLogInAttemptQueue;
//
//    @Value("${spring.rabbitmq.queue.respondLoggedInQueue}")
//    private String respondLoggedInQueue;
//
//    @Bean
//    public Queue receiveLogInAttemptQueue() {
//        return new Queue(receiveLogInAttemptQueue, true);
//    }
//
//    @Bean
//    public Queue respondLoggedInQueue() {
//        return new Queue(respondLoggedInQueue, true);
//    }
//
//    @Bean
//    public TopicExchange logInTopic() {
//        return new TopicExchange(logInTopic);
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
//        cachingConnectionFactory.setUsername(username);
//        cachingConnectionFactory.setPassword(password);
//        cachingConnectionFactory.setPort(Integer.parseInt(port));
//        return cachingConnectionFactory;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
//}
