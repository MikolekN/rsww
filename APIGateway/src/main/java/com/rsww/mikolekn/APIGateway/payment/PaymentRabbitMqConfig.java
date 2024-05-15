package com.rsww.mikolekn.APIGateway.payment;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRabbitMqConfig {

    @Value("${spring.rabbitmq.queue.trips.reservations.payment}")
    private String reservationPayment;

    @Bean
    public Queue reservationPayment() {
        return new Queue(reservationPayment);
    }
}
