package com.example.notelook.service;

import com.example.notelook.model.entity.User;
import com.example.notelook.model.enums.Role;
import com.example.notelook.repository.UserRepository;
import com.example.notelook.model.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @InjectMocks
    private CustomUserDetailsService service;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void testLoadUserByUsername_UserFound() {
        // given
        String username = "testUser";
        User expected = new User();
        expected.setUsername(username);

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expected));

        UserDetails actual = service.loadUserByUsername(username);

        // then
        assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // given
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // then
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(username));
    }

    @Test
    void testCreateUser() {
        // given
        String name = "John Doe";
        String username = "johndoe";
        String password = "password";
        Role role = Role.ROLE_USER;
        Set<Role> authorities = Set.of(role);
        String encodedPassword = "encodedPassword";
        CreateUserRequest request = new CreateUserRequest(name, username, password, authorities);
        User expected = User.builder()
                .name(name)
                .username(username)
                .password(encodedPassword)
                .authorities(authorities)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();

        // when
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(expected);
        User actual = service.createUser(request);

        // then
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getAuthorities().iterator().next().getAuthority(), actual.getAuthorities().iterator().next().getAuthority());
        assertTrue(actual.isAccountNonExpired());
        assertTrue(actual.isCredentialsNonExpired());
        assertTrue(actual.isEnabled());
        assertTrue(actual.isAccountNonLocked());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
