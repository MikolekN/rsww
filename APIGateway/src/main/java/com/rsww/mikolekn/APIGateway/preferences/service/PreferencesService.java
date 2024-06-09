package com.rsww.mikolekn.APIGateway.preferences.service;

import com.rsww.mikolekn.APIGateway.payment.service.PaymentService;
import com.rsww.mikolekn.APIGateway.preferences.dto.PreferencesRequest;
import com.rsww.mikolekn.APIGateway.preferences.dto.PreferencesResponse;
import com.rsww.mikolekn.APIGateway.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PreferencesService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue getPreferences;

    @Autowired
    public PreferencesService(RabbitTemplate rabbitTemplate, Queue getPreferences) {
        this.rabbitTemplate = rabbitTemplate;
        this.getPreferences = getPreferences;
    }

    public ResponseEntity<PreferencesResponse> getPreferences(String username) {
        String requestNumber = RequestUtils.generateRequestNumber();
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a preferences request with uuid: {}", requestNumber, uuid);

        try {
            PreferencesRequest preferencesRequest = new PreferencesRequest(uuid, username);
            PreferencesResponse preferencesResponse = rabbitTemplate.convertSendAndReceiveAsType(
                    getPreferences.getName(),
                    preferencesRequest,
                    new ParameterizedTypeReference<>() {}
            );
            logger.info("{} Received a preferences response: {}", requestNumber, preferencesResponse);
            return RequestUtils.prepareResponse(preferencesResponse);
        } catch (Exception e) {
            logger.error("{} Exception during preferences request process: {}", requestNumber, e.getMessage());
            return ResponseEntity.status(500).build();
        }

    }
}
