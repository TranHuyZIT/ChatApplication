package com.tghuy.SessionAuth.repositories;

import com.tghuy.SessionAuth.models.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Data
public class UserOnlineRepository {
    List<User> userOnline = new ArrayList<>();

    public List<User> addUserOnline(User user){
        userOnline.add(user);
        return this.userOnline;
    }
    public List<User> removeUserOnline(User user){
        userOnline.remove(user);
        return this.userOnline;
    }
}
