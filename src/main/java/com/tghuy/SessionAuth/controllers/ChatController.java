package com.tghuy.SessionAuth.controllers;

import com.tghuy.SessionAuth.models.ChatMessage;
import com.tghuy.SessionAuth.models.User;
import com.tghuy.SessionAuth.repositories.UserOnlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;
import java.util.List;

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
    @Autowired
    private UserOnlineRepository userOnlineRepository;

    @SubscribeMapping("/chat.users-online")
    public List<User> returnNumberOfUsersOnline(){
        return userOnlineRepository.getUserOnline();
    }

    @MessageMapping("/chat.message")
    public ChatMessage filterMessage(@Payload ChatMessage chatMessage, Principal principal){
        chatMessage.setFromUsername(principal.getName());
        return chatMessage;
    }
    @MessageMapping("/chat.message.private.${username}")
    public ChatMessage filterPrivateMessage(@Payload ChatMessage chatMessage,
                                            @DestinationVariable("username") String username, Principal principal){
        chatMessage.setFromUsername(principal.getName());
        String destination = "/user/" + username + "/exchange/amq.direct/chat.message";
        messagingTemplate.convertAndSend(destination, chatMessage);
        return chatMessage;
    }
}
