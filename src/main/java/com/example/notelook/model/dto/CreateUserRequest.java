package com.example.notelook.model.dto;

import com.example.notelook.model.enums.Role;

import java.util.Set;

public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
){
}