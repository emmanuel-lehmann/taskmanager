package com.taotechs.taskmanager.controller;

import com.taotechs.taskmanager.model.User;
import com.taotechs.taskmanager.security.JwtTokenProvider;
import com.taotechs.taskmanager.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtProvider;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        Map<String, String> request = Map.of("username", "testuser", "password", "password123");
        User mockUser = new User("testuser", "encodedpassword");

        when(authService.register("testuser", "password123")).thenReturn(mockUser);
        when(authService.authenticate("testuser", "password123")).thenReturn(authentication);
        when(jwtProvider.generateToken(authentication)).thenReturn("mocked-jwt-token");
        when(authentication.getName()).thenReturn("testuser");

        ResponseEntity<?> response = authController.register(request);

        assertEquals(200, response.getStatusCode().value());
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertEquals("mocked-jwt-token", responseBody.get("token"));
        assertEquals("testuser", responseBody.get("user"));
    }

    @Test
    void testRegisterMissingFields() {
        Map<String, String> request = Map.of("username", "testuser");

        ResponseEntity<?> response = authController.register(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Username and password are required", response.getBody());
    }

    @Test
    void testLoginSuccess() {
        Map<String, String> request = Map.of("username", "testuser", "password", "password123");

        when(authService.authenticate("testuser", "password123")).thenReturn(authentication);
        when(jwtProvider.generateToken(authentication)).thenReturn("mocked-jwt-token");
        when(authentication.getName()).thenReturn("testuser");

        ResponseEntity<?> response = authController.login(request);

        assertEquals(200, response.getStatusCode().value());
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertEquals("mocked-jwt-token", responseBody.get("token"));
        assertEquals("testuser", responseBody.get("user"));
    }

    @Test
    void testLoginMissingFields() {
        Map<String, String> request = Map.of("username", "testuser");

        ResponseEntity<?> response = authController.login(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(Map.of("message", "Username and password are required"), response.getBody());
    }

    @Test
    void testLoginInvalidCredentials() {
        Map<String, String> request = Map.of("username", "testuser", "password", "wrongpassword");

        when(authService.authenticate("testuser", "wrongpassword")).thenThrow(new RuntimeException("Invalid username or password"));

        ResponseEntity<?> response = authController.login(request);

        assertEquals(401, response.getStatusCode().value());
        assertEquals(Map.of("message", "Invalid username or password"), response.getBody());
    }
}
