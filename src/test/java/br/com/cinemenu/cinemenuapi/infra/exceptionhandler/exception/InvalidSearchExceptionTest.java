package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InvalidSearchExceptionTest {

    @Test
    @DisplayName("Test creating InvalidSearchException with message")
    void createInvalidSearchExceptionWithMessage() {
        // Given
        String message = "Invalid search query";

        // When
        InvalidSearchException exception = new InvalidSearchException(message);

        // Then
        Assertions.assertEquals(message, exception.getMessage());
    }
}
