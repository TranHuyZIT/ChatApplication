package com.tghuy.SessionAuth.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProtectedController {
    @GetMapping("/user-route")
    public String getUserPage(){
        System.out.println("Accessed to user-route");
        return "user";
    }
    @GetMapping("/admin-route")
    public String getAdminPage(){
        return "admin";
    }
}
