package com.rsww.mikolekn.APIGateway.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class LoginService {
    private final RabbitTemplate rabbitTemplate;
    private final Queue loginRequestQueue;
    static Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    LoginService(RabbitTemplate rabbitTemplate, Queue loginRequestQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.loginRequestQueue = loginRequestQueue;
    }

    ResponseEntity<Boolean> login(LoginDto loginDto) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a request with uuid: {}", requestNumber, uuid);

        LoginResponse loginResponse = (LoginResponse) rabbitTemplate.convertSendAndReceive(
                loginRequestQueue.getName(),
                new LoginRequest(uuid, loginDto.username(), loginDto.password()).toJSON());
        logger.info("{} Received a response: {}", requestNumber, loginResponse);

        logger.info("{} Finished a request with uuid: {}", requestNumber, uuid);
        if (loginResponse != null) {
            return new ResponseEntity<>(loginResponse.isResponse(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
