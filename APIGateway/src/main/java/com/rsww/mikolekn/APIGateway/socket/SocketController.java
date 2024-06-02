package com.rsww.mikolekn.APIGateway.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    /*
        Socket to which messages are sent
        Path: /app/hello
     */
    @MessageMapping("/hello")
    public void greeting(/* message */) throws Exception {
        // Do sth with received message
    }
}
