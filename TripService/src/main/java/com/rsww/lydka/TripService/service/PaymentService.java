package com.rsww.lydka.TripService.service;

import com.rsww.lydka.TripService.listener.events.payment.command.PayForReservationCommand;
import com.rsww.lydka.TripService.listener.events.payment.response.PaymentResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

@Component
public class PaymentService {
    private final RabbitTemplate template;
    private final String paymentQueueName;

    @Autowired
    public PaymentService(RabbitTemplate template, @Value("${spring.rabbitmq.queue.requestPaymentQueue}") String paymentQueueName) {
        this.template = template;
        this.paymentQueueName = paymentQueueName;
    }

    public PaymentResponse paymentRequest(PayForReservationCommand payment) {
        return template.convertSendAndReceiveAsType(
                paymentQueueName,
                payment,
                new ParameterizedTypeReference<>() {}
        );
    }
}
