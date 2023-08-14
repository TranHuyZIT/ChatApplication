package com.tghuy.SessionAuth.controllers;

import ch.qos.logback.core.model.Model;
import com.tghuy.SessionAuth.models.DTO.LoginDTO;
import com.tghuy.SessionAuth.models.DTO.RegisterDTO;
import com.tghuy.SessionAuth.models.Roles;
import com.tghuy.SessionAuth.models.User;
import com.tghuy.SessionAuth.repositories.RoleRepository;
import com.tghuy.SessionAuth.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/auth/login")
    public String loginPage (@ModelAttribute LoginDTO loginDTO, HttpSession session){
        Object userSession = session.getAttribute("session_user");
        if (userSession != null) return "redirect:/home";
        return "auth/login";
    }
    @PostMapping("/auth/login")
    public String login(@Valid @ModelAttribute LoginDTO loginDTO, BindingResult errors, ModelMap model,
                        HttpSession httpSession){
        System.out.println(loginDTO);
        if (errors.hasErrors()) {
            return "auth/login"; // Assuming "login" is the name of your Thymeleaf login page
        }
        // Login
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            httpSession.setAttribute("session_user", loginDTO.getUsername());
            return "redirect:/main";
        }
        catch (BadCredentialsException e){
            System.out.println(e);
            errors.addError(new ObjectError("authentication", e.getMessage()));
            model.addAttribute("authenticationError", "Bad credential, please re-check your info.");
            return "auth/login";
        }
        catch (Exception e){
            System.out.println(e);
            errors.addError(new ObjectError("authentication", e.getMessage()));
            model.addAttribute("authenticationError", "Error occured, please try again later!");
            return "auth/login";
        }
    }
    @GetMapping("/auth/register")
    public String register(@ModelAttribute RegisterDTO registerDTO){
        return "auth/register";
    }
    @PostMapping("/auth/register")
    public String register(@Valid @ModelAttribute RegisterDTO registerDTO, BindingResult errors, ModelMap model){
        if (errors.hasErrors()) {
            return "auth/register"; // Assuming "login" is the name of your Thymeleaf login page
        }
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()){
            model.addAttribute("registerError", "Username had already been taken.");
            return "auth/register";
        }
        Optional<Roles> userRole = roleRepository.findByName("USER");
        if(userRole.isEmpty()) {
            model.addAttribute("registerError", "Role user is missing from system.");
            return "auth/register";
        }
        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(userRole.get());
        User user = User.builder()
                .username(registerDTO.getUsername())
                .fullName(registerDTO.getFullName())
                .password(new BCryptPasswordEncoder().encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .rolesList(rolesList)
                .build();
        userRepository.save(user);
        return "redirect:/auth/login";
    }
    @PostMapping("/auth/logout")
    public String logout (HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session);
        session.invalidate();
        return "redirect:/home";
    }
}
