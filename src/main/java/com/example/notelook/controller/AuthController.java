package com.example.notelook.controller;

import com.example.notelook.model.dto.AuthRequest;
import com.example.notelook.model.dto.CreateUserRequest;
import com.example.notelook.model.entity.User;
import com.example.notelook.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok().body("Hello World!");
    }

    @PostMapping("")
    public ResponseEntity<User> signUp(@RequestBody CreateUserRequest request) {
        User user = service.signUp(request);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        var token = service.login(request);
        if (token != null) {
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserString() {
        return ResponseEntity.ok().body("This is USER!");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminString() {
        return ResponseEntity.ok().body("This is ADMIN!");
    }
}
