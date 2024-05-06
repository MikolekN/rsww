package com.rsww.mikolekn.APIGateway.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

import static com.rsww.mikolekn.APIGateway.utils.RequestUtils.prepareResponse;

@Service
public class LoginService {
    private final RabbitTemplate rabbitTemplate;
    static Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final Queue loginQueue;

    @Autowired
    LoginService(RabbitTemplate rabbitTemplate, Queue loginQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.loginQueue = loginQueue;
    }

    ResponseEntity<Boolean> login(LoginDto loginDto) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a login request with uuid: {}", requestNumber, uuid);

        LoginResponse loginResponse = rabbitTemplate.convertSendAndReceiveAsType(
                loginQueue.getName(),
                new LoginRequest(uuid, loginDto.username(), loginDto.password()),
                new ParameterizedTypeReference<>() {});
        logger.info("{} Received a login response: {}", requestNumber, loginResponse != null && loginResponse.isResponse());
        return prepareResponse(loginResponse);
    }
}
