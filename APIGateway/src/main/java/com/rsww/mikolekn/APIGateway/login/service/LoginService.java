package com.rsww.mikolekn.APIGateway.login.service;

import com.rsww.mikolekn.APIGateway.login.dto.LoginDto;
import com.rsww.mikolekn.APIGateway.login.dto.LoginRequest;
import com.rsww.mikolekn.APIGateway.login.dto.LoginResponse;
import com.rsww.mikolekn.APIGateway.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue loginQueue;

    @Autowired
    public LoginService(RabbitTemplate rabbitTemplate, Queue loginQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.loginQueue = loginQueue;
    }

    public ResponseEntity<LoginResponse> login(LoginDto loginDto) {
        String requestNumber = RequestUtils.generateRequestNumber();
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a login request with uuid: {}", requestNumber, uuid);

        try {
            LoginRequest loginRequest = new LoginRequest(uuid, loginDto.username(), loginDto.password());
            LoginResponse loginResponse = rabbitTemplate.convertSendAndReceiveAsType(
                    loginQueue.getName(),
                    loginRequest,
                    new ParameterizedTypeReference<>() {}
            );
            logger.info("{} Received a login response: {}", requestNumber, loginResponse != null && loginResponse.isResponse());
            return RequestUtils.prepareResponse(loginResponse, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("{} Exception during login process: {}", requestNumber, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
