package com.rsww.mikolekn.APIGateway.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    private final SimpMessagingTemplate template;
    private final SocketService socketService;

    @Autowired
    SocketController(SimpMessagingTemplate template, SocketService socketService){
        this.template = template;
        this.socketService = socketService;
    }

    /* /app/send/message */
    @MessageMapping("/topic/getUserPreferences")
    public void getUserPreferences(String username){
        this.socketService.sendUserPreferences(username);
    }
}
