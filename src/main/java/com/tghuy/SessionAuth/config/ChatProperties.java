package com.tghuy.SessionAuth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Data
@ConfigurationProperties(prefix = "chat")
public class ChatProperties {
    private int maxProfanity;
    private Set<String> bannedWords;

}
