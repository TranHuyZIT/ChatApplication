package com.tghuy.SessionAuth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


/**
 * A WebSocket interaction begins with an HTTP request that uses the HTTP Upgrade header
 * to upgrade or, in this case, to switch to the WebSocket protocol.
 *
 * Instead of the usual 200 status code, a server with WebSocket support returns output of switching protocol.
 *
 * After a successful handshake, the TCP socket underlying the HTTP upgrade request remains open
 * for both the client and the server to continue to send and receive messages.
 *
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    @Override
    protected void configureStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/chat-websocket").withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/queue", "/topic", "/exchange");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
