package com.rsww.lydka.paymentms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsww.lydka.paymentms.payment.response.PaymentReqeust;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO usunąć ten controller, on jest tylko na potrzeby testowe

@RestController
public class RabbitMqController {

    private RabbitTemplate rabbitTemplate;
    private Queue requestPaymentQueue;

    @Autowired
    RabbitMqController(RabbitTemplate rabbitTemplate, Queue requestPaymentQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.requestPaymentQueue = requestPaymentQueue;
    }

    @GetMapping("/sendMessage")
    public String sendMessage() {
        ObjectMapper mapper = new ObjectMapper();
        PaymentReqeust paymentReqeust = new PaymentReqeust("ORDER_PAYMENT_INITIATED");
        try {
            String message = mapper.writeValueAsString(paymentReqeust);
            rabbitTemplate.convertAndSend(requestPaymentQueue.getName(), message);
            return "Message sent to the RabbitMQ Successfully";
        } catch (JsonProcessingException e) {
            return "Error while sending message";
        }
    }
}
