package com.example.notelook.service;

import com.example.notelook.model.dto.AuthRequest;
import com.example.notelook.model.dto.CreateUserRequest;
import com.example.notelook.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
public class AuthService {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userService;
    public AuthService(JWTService jwtService, AuthenticationManager authenticationManager, CustomUserDetailsService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public String login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        log.info("invalid username " + request.username());
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

    public User signUp(CreateUserRequest request) {
        return userService.createUser(request);
    }
}
