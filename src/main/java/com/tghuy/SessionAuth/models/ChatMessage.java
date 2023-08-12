package com.tghuy.SessionAuth.models;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ChatMessage {
    String fromUsername;
    String message;
    @Temporal(TemporalType.TIMESTAMP)
    Date timeSent;
}
