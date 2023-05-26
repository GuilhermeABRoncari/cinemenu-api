package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InvalidApiKeyExceptionTest {

    @Test
    @DisplayName("Test creating InvalidApiKeyException with message")
    void createInvalidApiKeyExceptionWithMessage() {
        // Given
        String message = "Invalid API key";

        // When
        InvalidApiKeyException exception = new InvalidApiKeyException(message);

        // Then
        Assertions.assertEquals(message, exception.getMessage());
    }
}
