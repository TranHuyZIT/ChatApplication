package com.tghuy.SessionAuth.services;

import com.tghuy.SessionAuth.models.User;
import com.tghuy.SessionAuth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepository.findByUsername(username);
        return new com.tghuy.SessionAuth.config.UserDetails(userInfo
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!")));
    }
}
