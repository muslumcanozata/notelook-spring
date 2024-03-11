package com.example.notelook.controller;

import com.example.notelook.model.dto.AuthRequest;
import com.example.notelook.model.dto.CreateUserRequest;
import com.example.notelook.model.entity.User;
import com.example.notelook.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.notelook.service.AuthService;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @InjectMocks
    private AuthController controller;
    @Mock
    private AuthService authService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testWelcome() throws Exception {
        mockMvc.perform(get("/welcome"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }

    @Test
    public void testSignUp_UserCreated() throws Exception {
        //given
        CreateUserRequest createUserRequest = new CreateUserRequest("name", "username", "password", Set.of(Role.ROLE_USER));
        User mockUser = new User();

        //when
        when(authService.signUp(createUserRequest)).thenReturn(mockUser);

        //then
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.writeObjAsBytes(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testSignUp_BadRequest() throws Exception {
        //given
        CreateUserRequest createUserRequest = new CreateUserRequest("name", "username", "password", Set.of(Role.ROLE_USER));

        //when
        when(authService.signUp(createUserRequest)).thenReturn(null);

        //then
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.writeObjAsBytes(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_ValidCredentials() throws Exception {
        //given
        AuthRequest authRequest = new AuthRequest("username", "password");

        //when
        when(authService.login(authRequest)).thenReturn("mockToken");

        //then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.writeObjAsBytes(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("mockToken"));
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        //given
        AuthRequest authRequest = new AuthRequest("username", "password");

        //when
        when(authService.login(authRequest)).thenReturn(null);

        //then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.writeObjAsBytes(authRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid username or password"));
    }

    @Test
    public void testGetUserString() throws Exception {
        mockMvc.perform(get("/auth/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is USER!"));
    }

    @Test
    public void testGetAdminString() throws Exception {
        mockMvc.perform(get("/auth/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is ADMIN!"));
    }
}