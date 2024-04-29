package com.rsww.mikolekn.APIGateway.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
    static Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final DirectExchange exchange;

    @Autowired
    LoginService(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    ResponseEntity<Boolean> login(LoginDto loginDto) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a request with uuid: {}", requestNumber, uuid);

        LoginResponse loginResponse = LoginResponse.fromJSON( (String) rabbitTemplate.convertSendAndReceive(
                exchange.getName(),
                "request",
                new LoginRequest(uuid, loginDto.username(), loginDto.password()).toJSON()));
        logger.info("{} Received a response: {}", requestNumber, loginResponse);
        if (loginResponse != null) {
            if (loginResponse.isResponse()) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
