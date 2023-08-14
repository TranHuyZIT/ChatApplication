package com.tghuy.SessionAuth.repositories;

import com.tghuy.SessionAuth.models.User;
import com.tghuy.SessionAuth.models.events.LoginEvent;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Data
public class UserOnlineRepository {
    List<LoginEvent> userOnline = new ArrayList<>();

    public List<LoginEvent> addUserOnline(LoginEvent user){
        userOnline.add(user);
        return this.userOnline;
    }
    public List<LoginEvent> removeUserOnline(LoginEvent user){
        userOnline.remove(user);
        return this.userOnline;
    }
}
