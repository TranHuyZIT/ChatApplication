package com.tghuy.SessionAuth.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToHome(){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String homepage(){
        return "index";
    }
}
