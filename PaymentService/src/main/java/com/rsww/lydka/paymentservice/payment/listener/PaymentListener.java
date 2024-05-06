package com.rsww.lydka.paymentservice.payment.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsww.lydka.paymentservice.payment.response.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentListener {
    @Value("${payment.service.rejection.probability}")
    private int rejectProbability;

    @Value("${payment.service.message}")
    private String queueMessage;

    private static final Logger log = LoggerFactory.getLogger(PaymentListener.class);


    private final RabbitTemplate rabbitTemplate;
    private final Queue requestPaymentQueue;
    private final Queue responsePaymentQueue;

    @Autowired
    public PaymentListener(RabbitTemplate rabbitTemplate, Queue requestPaymentQueue, Queue responsePaymentQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.requestPaymentQueue = requestPaymentQueue;
        this.responsePaymentQueue = responsePaymentQueue;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.requestPaymentQueue}")
    public void makePayment(String message) {
        log.info("Received a payment message: {}", message);

        // TODO walidacja typu wiadomości

        try {
            Random r = new Random();
            boolean status = r.nextInt(101) >= rejectProbability;
            PaymentResponse paymentResponse = new PaymentResponse(queueMessage, status);
            ObjectMapper mapper = new ObjectMapper();
            String answer = mapper.writeValueAsString(paymentResponse);
            rabbitTemplate.convertAndSend(responsePaymentQueue.getName(), answer);
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage());
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.responsePaymentQueue}")
    public void responsePayment(String message) {
        log.info("Response message: {}", message);
    }
}
