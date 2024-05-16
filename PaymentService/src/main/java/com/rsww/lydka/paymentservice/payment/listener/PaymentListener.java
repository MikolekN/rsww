package com.rsww.lydka.paymentservice.payment.listener;

import com.rsww.lydka.paymentservice.payment.response.PaymentRequest;
import com.rsww.lydka.paymentservice.payment.response.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentListener {
    @Value("${payment.service.rejection.probability}")
    private int rejectProbability;
    static Logger logger = LoggerFactory.getLogger(PaymentListener.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue.requestPaymentQueue}")
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        logger.info("{} Received a payment request.", requestNumber);

        if (paymentRequest != null) {
            Random r = new Random();
            boolean status = r.nextInt(101) >= rejectProbability;
            logger.info("{} {} payment.", requestNumber, (status ? "Successful" : "Unsuccessful"));
            return new PaymentResponse(paymentRequest.getUuid(), status, paymentRequest.getReservationId());
        }
        else {
            logger.info("{} Could not deserialize the received message.", requestNumber);
            return new PaymentResponse(null, false, null);
        }
    }
}
