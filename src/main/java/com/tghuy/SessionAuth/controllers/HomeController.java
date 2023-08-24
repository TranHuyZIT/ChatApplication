package com.tghuy.SessionAuth.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToHome(HttpSession session, ModelMap model){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String homepage(HttpSession session, ModelMap model, Principal principal){
        System.out.println(principal);
        Object userSession = session.getAttribute("session_user");
        if (userSession != null) model.addAttribute("username", userSession);
        return "index";
    }
    @GetMapping("/error")
    public String errorPage(){
        return "error";
    }
}
