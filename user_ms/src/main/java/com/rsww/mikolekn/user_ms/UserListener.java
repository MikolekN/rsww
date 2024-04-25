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
        logger.debug("{} Received a message.", requestNumber);

        if (loginRequest != null) {
            boolean userExists = UserRepository.userExists(loginRequest);
            if (userExists) {
                logger.debug("{} Successful login attempt for username {}.", requestNumber, loginRequest.getUsername());
                LoginResponse loginResponse = new LoginResponse(loginRequest.getUuid(), true);
                rabbitTemplate.convertAndSend(loginResponseQueue.getName(), loginResponse.toJSON());
            } else {
                logger.debug("{} Unsuccessful login attempt for username {}.", requestNumber, loginRequest.getUsername());
                LoginResponse loginResponse = new LoginResponse(loginRequest.getUuid(), false);
                rabbitTemplate.convertAndSend(loginResponseQueue.getName(), loginResponse.toJSON());
            }
        } else {
            logger.debug("{} Could not deserialize the received message.", requestNumber);
        }
    }

    // TEMPORARY - message will be received by API Gateway
    @RabbitListener(queues = "${spring.rabbitmq.queue.loginResponseQueue}")
    public void receiveMessage2(String message) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        LoginResponse loginResponse = LoginResponse.fromJSON(message);
        if (loginResponse != null) {
            logger.debug("{} Login response received {}, {}.", requestNumber, loginResponse.getUuid(), loginResponse.isResponse());
        } else {
            logger.debug("{} Could not deserialize the received message.", requestNumber);
        }
    }
}
