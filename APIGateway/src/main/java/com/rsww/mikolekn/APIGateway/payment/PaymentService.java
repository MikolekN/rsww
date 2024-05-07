package com.rsww.mikolekn.APIGateway.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
public class PaymentService {
    private final RabbitTemplate rabbitTemplate;
    static Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final Queue paymentQueue;

    @Autowired
    PaymentService(RabbitTemplate rabbitTemplate, Queue paymentQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentQueue = paymentQueue;
    }

    ResponseEntity<Boolean> payment(PaymentDto paymentDto) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a payment request with uuid: {}", requestNumber, uuid);

        PaymentResponse paymentResponse = rabbitTemplate.convertSendAndReceiveAsType(
                paymentQueue.getName(),
                new PaymentRequest(uuid, paymentDto.paymentId()),
                new ParameterizedTypeReference<>() {});
        logger.info("{} Received a payment response: {}", requestNumber, paymentResponse);

        if (paymentResponse != null && paymentResponse.isResponse()) {
            // TODO: send payment confirmation to Order microservice
        }

        return prepareResponse(paymentResponse);
    }


}