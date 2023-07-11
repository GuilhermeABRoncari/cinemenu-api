package br.com.cinemenu.cinemenuapi.infra.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenServiceTest {
    @Mock
    private Algorithm algorithm;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken_WithInvalidUser_ShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> tokenService.generateToken(null));
    }

    @Test
    void testGetSubject_WithInvalidToken_ShouldThrowException() {
        // Arrange
        String token = "invalid_token";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> tokenService.getSubject(token));
    }

    @Test
    void testExpiration() {
        // Act
        Instant expiration = tokenService.expiration();

        // Assert
        assertNotNull(expiration);
    }
}
