package com.tghuy.SessionAuth.controllers;

import com.tghuy.SessionAuth.models.User;
import com.tghuy.SessionAuth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/main")
    public String getMainPage(Principal principal, Model model){
        String username = principal == null ? null : principal.getName();
        User currentUser = userRepository.findByUsername(username).orElse(null);
        model.addAttribute("currentUser", currentUser);
        return "chat/main";
    }
}
