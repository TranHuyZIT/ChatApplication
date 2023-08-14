package com.tghuy.SessionAuth.models.events;

import com.tghuy.SessionAuth.models.User;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LoginEvent {
    User user;
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
}
