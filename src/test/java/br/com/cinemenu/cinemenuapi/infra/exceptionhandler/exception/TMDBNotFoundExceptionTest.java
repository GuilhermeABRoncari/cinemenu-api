package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TMDBNotFoundExceptionTest {

    @Test
    @DisplayName("Test creating TMDBNotFoundException whit message")
    void test() {
        // Given
        String message = "not found";

        // When
        TMDBNotFoundException tmdbNotFoundException = new TMDBNotFoundException(message);

        // Then
        assertEquals(tmdbNotFoundException.getMessage(), message);
    }
}