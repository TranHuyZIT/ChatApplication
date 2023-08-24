package com.tghuy.SessionAuth.config;

import com.tghuy.SessionAuth.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private User userInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userInfo.getRolesList().stream().map((roles ->
                new SimpleGrantedAuthority(roles.getName()))).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
