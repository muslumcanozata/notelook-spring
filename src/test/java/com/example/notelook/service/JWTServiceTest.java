package com.example.notelook.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {
    @InjectMocks
    private JWTService service;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("jwt.key", "secret");
    }

    @Test
    void testGenerateToken() {
        //given
        String userName = "testUser";
        Map<String, Object> expectedClaims = new HashMap<>();
        expectedClaims.put("sub", userName);
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // Expiration time in 1 hour

        //when
        String token = service.generateToken(userName);

        //then
        Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
        assertEquals(expectedClaims.get("sub"), claims.getSubject());
        assertEquals(true, claims.getExpiration().after(new Date()));
    }

}
