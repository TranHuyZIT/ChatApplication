package com.tghuy.SessionAuth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * @MessageMapping("/hello") annotation ensures that, if a message is sent to the /hello destination,
 * the function() below it is called.
 *
 *  @SendTo("/topic/greetings") broadcast the return value of function below it to all subscribers of "/topic/greetings"
 */

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/users-online")
    public long returnNumberOfUsersOnline(){
        return 1L;
    }

}
