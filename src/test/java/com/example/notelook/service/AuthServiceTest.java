package com.example.notelook.service;

import com.example.notelook.model.dto.AuthRequest;
import com.example.notelook.model.dto.CreateUserRequest;
import com.example.notelook.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService service;

    @Mock
    private JWTService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CustomUserDetailsService userService;

    @Test
    void testLogin_ValidCredentials() {
        //given
        AuthRequest request = new AuthRequest("validUsername", "validPassword");
        String expected = "mockedJWTToken";
        //when
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken("validUsername")).thenReturn(expected);

        String actual = service.login(request);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void testLogin_InvalidCredentials() {
        //given
        AuthRequest request = new AuthRequest("invalidUsername", "invalidPassword");
        Authentication authentication = mock(Authentication.class);

        //when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        //then
        assertThrows(UsernameNotFoundException.class, () -> service.login(request));
    }

    @Test
    void testSignUp() {
        //given
        CreateUserRequest request = new CreateUserRequest("name", "username", "password", null);
        Long id = 1L;
        User expected = new User();
        expected.setId(id);

        //when
        when(userService.createUser(request)).thenReturn(expected);

        User actual = service.signUp(request);

        //then
        assertNotNull(expected);
        assertEquals(expected.getId(), actual.getId());
    }
}
