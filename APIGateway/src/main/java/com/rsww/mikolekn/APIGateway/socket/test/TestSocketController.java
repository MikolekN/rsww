package com.rsww.mikolekn.APIGateway.socket.test;

import com.rsww.mikolekn.APIGateway.socket.SocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socket")
public class TestSocketController {
    private static final Logger log = LoggerFactory.getLogger(com.rsww.mikolekn.APIGateway.payment.controller.PaymentController.class);
    private final SocketService socketService;

    public TestSocketController(SocketService socketService) {
        this.socketService = socketService;
    }


    @GetMapping
    public String testSocket() {
//        socketService.sendOfferReservedInfo();
        return "sended";
    }
}