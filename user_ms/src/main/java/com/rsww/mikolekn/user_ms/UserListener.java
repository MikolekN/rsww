package com.rsww.mikolekn.user_ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class UserListener {
    static Logger logger = LoggerFactory.getLogger(UserListener.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Qualifier("loginResponseQueue")
    @Autowired
    private Queue loginResponseQueue;

//    @RabbitListener(queues = RswwUserApplication.MESSAGE_QUEUE)
//    public void receiveMessage(UserMessage message) {
//        logger.info("Received login attempt from {} with password {}.", message.getUsername(), message.getPassword());
//    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.loginRequestQueue}")
    public void receiveMessage(String message) {
        UserMessage receivedUser = UserMessage.fromJSON(message);
        logger.info("Received message {}.", message);

        if (receivedUser != null) {
            boolean userExists = UserRepository.userExists(receivedUser);
            if (userExists) {
                logger.info("User with username '{}' and password '{}' exists.", receivedUser.getUsername(), receivedUser.getPassword());
                String responseMessage = "User with username '" + receivedUser.getUsername() + "' exists.";
                rabbitTemplate.convertAndSend(loginResponseQueue.getName(), responseMessage);
            } else {
                logger.info("User with username '{}' and password '{}' does not exist.", receivedUser.getUsername(), receivedUser.getPassword());
                String responseMessage = "User with username '" + receivedUser.getUsername() + "' doesn't exist.";
                rabbitTemplate.convertAndSend(loginResponseQueue.getName(), responseMessage);
            }
        } else {
            logger.info("Could not deserialize the received message.");
            String responseMessage = "Error in parsing the received message.";
            rabbitTemplate.convertAndSend(loginResponseQueue.getName(), responseMessage);
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.loginResponseQueue}")
    public void receiveMessage2(String message) {
        logger.info("Received message '{}'.", message);
    }

}
