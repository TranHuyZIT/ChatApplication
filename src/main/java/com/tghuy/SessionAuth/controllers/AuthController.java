package com.tghuy.SessionAuth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/auth/login")
    public String loginPage (){
        return "auth/login";
    }
}
