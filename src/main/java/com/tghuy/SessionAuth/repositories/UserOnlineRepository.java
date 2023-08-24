package com.tghuy.SessionAuth.repositories;

import com.tghuy.SessionAuth.models.User;
import com.tghuy.SessionAuth.models.events.LoginEvent;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Data
public class UserOnlineRepository {
    List<LoginEvent> userOnline = new ArrayList<>();

    public List<LoginEvent> addUserOnline(LoginEvent user){
        userOnline.add(user);
        return this.userOnline;
    }
    public List<LoginEvent> removeUserOnline(String username){
        userOnline.removeIf(userOnline -> Objects.equals(userOnline.getUser().getUsername(), username));
        return this.userOnline;
    }
}
