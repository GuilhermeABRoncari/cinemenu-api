package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JWTCineMenuExceptionTest {

    @Test
    @DisplayName("Test if exception message is set correctly")
    void testExceptionMessage() {
        // Given
        String errorMessage = "Invalid JWT token";

        // When
        JWTCineMenuException exception = new JWTCineMenuException(errorMessage);

        // Then
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }
}