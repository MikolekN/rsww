package com.rsww.mikolekn.APIGateway.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    private final SimpMessagingTemplate template;

    @Autowired
    SocketController(SimpMessagingTemplate template){
        this.template = template;
    }

    /* /app/send/message */
    @MessageMapping("/topic/message")
    public void sendMessage(String message){
        System.out.println(message);
    }
}
