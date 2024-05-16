package com.rsww.mikolekn.APIGateway.payment;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRabbitMqConfig {

    @Value("${spring.rabbitmq.queue.tripReservationPayment}")
    private String tripReservationPayment;

    @Bean
    public Queue tripReservationPayment() {
        return new Queue(tripReservationPayment);
    }
}
