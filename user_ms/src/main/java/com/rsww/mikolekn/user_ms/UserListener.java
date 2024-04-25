package com.rsww.mikolekn.user_ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserListener {
    static Logger logger = LoggerFactory.getLogger(UserListener.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Qualifier("loginResponseQueue")
    @Autowired
    private Queue loginResponseQueue;

    @RabbitListener(queues = "${spring.rabbitmq.queue.loginRequestQueue}")
    public void receiveMessage(String message) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        LoginRequest loginRequest = LoginRequest.fromJSON(message);
        logger.info("{} Received a message.", requestNumber);

        if (loginRequest != null) {
            boolean userExists = UserRepository.userExists(loginRequest);
            if (userExists) {
                logger.info("{} Successful login attempt for username {}.", requestNumber, loginRequest.getUsername());
                String responseMessage = String.format("{\"uuid\":\"%s\",\"response\":%b}", loginRequest.getUuid(), true);
                rabbitTemplate.convertAndSend(loginResponseQueue.getName(), responseMessage);
            } else {
                logger.info("{} Unsuccessful login attempt for username {}.", requestNumber, loginRequest.getUsername());
                String responseMessage = String.format("{\"uuid\":\"%s\",\"response\":%b}", loginRequest.getUuid(), false);
                rabbitTemplate.convertAndSend(loginResponseQueue.getName(), responseMessage);
            }
        } else {
            logger.info("{} Could not deserialize the received message.", requestNumber);
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.loginResponseQueue}")
    public void receiveMessage2(String message) {
        logger.info("Received message '{}'.", message);
    }

}
