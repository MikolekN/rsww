package com.rsww.mikolekn.APIGateway.payment.controller;

import com.rsww.mikolekn.APIGateway.payment.dto.PaymentDto;
import com.rsww.mikolekn.APIGateway.payment.dto.PaymentResponse;
import com.rsww.mikolekn.APIGateway.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> payment(@RequestBody PaymentDto paymentDto) {
        log.info("Payment request received for reservation ID: {}", paymentDto.reservationId());
        return paymentService.processPayment(paymentDto);
    }
}
