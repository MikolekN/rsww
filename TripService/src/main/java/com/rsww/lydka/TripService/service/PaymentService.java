package com.rsww.lydka.TripService.service;

import com.rsww.lydka.TripService.listener.events.payment.PaymentRequest;
import com.rsww.lydka.TripService.listener.events.payment.PaymentResponse;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import com.rsww.lydka.TripService.listener.events.payment.PostPayment;
import com.rsww.lydka.TripService.listener.events.payment.PostPaymentResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.CancelFlightReservationCommand;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.ReservationResponse;
import org.springframework.amqp.core.Queue;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class PaymentService {
    private final RabbitTemplate template;
    private final String paymentQueueName;

    @Autowired
    public PaymentService(RabbitTemplate template, @Value("${spring.rabbitmq.queue.requestPaymentQueue}") String paymentQueueName) {
        this.template = template;
        this.paymentQueueName = paymentQueueName;
    }

    public PaymentResponse paymentRequest(PaymentRequest payment) {

        PaymentResponse response = template.convertSendAndReceiveAsType(
                paymentQueueName,
                payment,
                new ParameterizedTypeReference<>() {}
        );
        return response;
    }
}
