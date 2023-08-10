package com.tghuy.SessionAuth.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToHome(HttpSession session, ModelMap model){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String homepage(HttpSession session, ModelMap model){
        Object userSession = session.getAttribute("session_user");
        if (userSession != null) model.addAttribute("username", userSession);
        return "index";
    }
}
