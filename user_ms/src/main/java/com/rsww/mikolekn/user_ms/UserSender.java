//package com.rsww.mikolekn.rsww_user;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//@EnableScheduling
//public class UserSender {
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//    @Scheduled(fixedRate = 1000L)
//    void sendMessage() {
//        UserMessage userMessage = new UserMessage("userX", "passwordX");
//        rabbitTemplate.convertAndSend(RswwUserApplication.MESSAGE_TOPIC, RswwUserApplication.MESSAGE_QUEUE, userMessage.toJSON());
//    }
//}
