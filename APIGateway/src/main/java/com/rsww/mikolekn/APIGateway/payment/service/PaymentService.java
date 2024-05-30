package com.rsww.mikolekn.APIGateway.payment.service;

import com.rsww.mikolekn.APIGateway.payment.dto.PaymentDto;
import com.rsww.mikolekn.APIGateway.payment.dto.PaymentRequest;
import com.rsww.mikolekn.APIGateway.payment.dto.PaymentResponse;
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
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue tripReservationPayment;

    @Autowired
    public PaymentService(RabbitTemplate rabbitTemplate, Queue tripReservationPayment) {
        this.rabbitTemplate = rabbitTemplate;
        this.tripReservationPayment = tripReservationPayment;
    }

    public ResponseEntity<PaymentResponse> processPayment(PaymentDto paymentDto) {
        String requestNumber = RequestUtils.generateRequestNumber();
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a payment request with uuid: {}", requestNumber, uuid);

        try {
            PaymentRequest paymentRequest = new PaymentRequest(uuid, paymentDto.reservationId());
            PaymentResponse paymentResponse = rabbitTemplate.convertSendAndReceiveAsType(
                    tripReservationPayment.getName(),
                    paymentRequest,
                    new ParameterizedTypeReference<>() {}
            );
            logger.info("{} Received a payment response: {}", requestNumber, paymentResponse);
            return RequestUtils.prepareResponse(paymentResponse);
        } catch (Exception e) {
            logger.error("{} Exception during payment process: {}", requestNumber, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
