package com.rsww.mikolekn.UserService.listener;

import com.rsww.mikolekn.UserService.dto.LoginRequest;
import com.rsww.mikolekn.UserService.dto.LoginResponse;
import com.rsww.mikolekn.UserService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserListener {
    private static final Logger logger = LoggerFactory.getLogger(UserListener.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue.loginQueue}")
    public LoginResponse receiveMessage(LoginRequest loginRequest) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        logger.info("{} Received a message.", requestNumber);

        if (loginRequest != null) {
            boolean userExists = UserRepository.userExists(loginRequest);
            if (userExists) {
                logger.info("{} Successful login attempt for username {}.", requestNumber, loginRequest.getUsername());
                return new LoginResponse(loginRequest.getUuid(), true);
            } else {
                logger.info("{} Unsuccessful login attempt for username {}.", requestNumber, loginRequest.getUsername());
                return new LoginResponse(loginRequest.getUuid(), false);
            }
        } else {
            logger.info("{} Could not deserialize the received message.", requestNumber);
            return new LoginResponse(null, false);
        }
    }
}
