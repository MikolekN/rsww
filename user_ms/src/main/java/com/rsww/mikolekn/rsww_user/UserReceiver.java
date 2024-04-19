package com.rsww.mikolekn.rsww_user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserReceiver {
    static Logger logger = LoggerFactory.getLogger(UserReceiver.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @RabbitListener(queues = RswwUserApplication.MESSAGE_QUEUE)
//    public void receiveMessage(UserMessage message) {
//        logger.info("Received login attempt from {} with password {}.", message.getUsername(), message.getPassword());
//    }

    @RabbitListener(queues = RswwUserApplication.MESSAGE_RECEIVE_QUEUE)
    public void receiveMessage(String message) {
        UserMessage receivedUser = UserMessage.fromJSON(message);
        logger.info("Received message {}.", message);

        if (receivedUser != null) {
            boolean userExists = UserRepository.userExists(receivedUser);
            if (userExists) {
                logger.info("User with username '{}' and password '{}' exists.", receivedUser.getUsername(), receivedUser.getPassword());
//                String responseMessage = "User with username '" + receivedUser.getUsername() + "' exists.";
//                rabbitTemplate.convertAndSend(RswwUserApplication.MESSAGE_SEND_QUEUE, responseMessage);
            } else {
                logger.info("User with username '{}' and password '{}' does not exist.", receivedUser.getUsername(), receivedUser.getPassword());
            }
        } else {
            logger.info("Could not deserialize the received message.");
        }
    }

}
