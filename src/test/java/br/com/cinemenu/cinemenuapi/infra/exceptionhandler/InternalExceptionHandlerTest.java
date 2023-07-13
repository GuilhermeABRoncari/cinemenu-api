package br.com.cinemenu.cinemenuapi.infra.exceptionhandler;

import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.JWTCineMenuException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.TMDBNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternalExceptionHandlerTest {

    @Test
    @DisplayName("Test handling InvalidSearchException")
    void handleInvalidSearchException() {
        // Given
        String errorMessage = "Invalid search query";
        InvalidSearchException exception = new InvalidSearchException(errorMessage);
        InternalExceptionHandler handler = new InternalExceptionHandler();

        // When
        ResponseEntity responseEntity = handler.handleInvalidSearchException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test handling InvalidApiKeyException")
    void handleInvalidApiKeyException() {
        // Given
        String errorMessage = "Invalid API key";
        InvalidApiKeyException exception = new InvalidApiKeyException(errorMessage);
        InternalExceptionHandler handler = new InternalExceptionHandler();

        // When
        ResponseEntity responseEntity = handler.handleInvalidApiKeyException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test handling IllegalArgumentException")
    void handleInvalidArgumentException() {
        // Given
        String errorMessage = "Invalid argument";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);
        InternalExceptionHandler handler = new InternalExceptionHandler();

        // When
        ResponseEntity responseEntity = handler.handleInvalidArgumentException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test handling TMDBNotFoundException")
    void handleTMDBNotFoundException() {
        // Given
        String errorMessage = "not found";
        TMDBNotFoundException exception = new TMDBNotFoundException(errorMessage);
        InternalExceptionHandler handler = new InternalExceptionHandler();

        //When
        ResponseEntity responseEntity = handler.notFoundResponseFromTMDBApi(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test handling JWTCineMenuException")
    void handleJWTCineMenuException() {
        // Given
        String errorMessage = "Invalid JWT token";
        JWTCineMenuException exception = new JWTCineMenuException(errorMessage);
        InternalExceptionHandler handler = new InternalExceptionHandler();

        // When
        ResponseEntity<String> responseEntity = handler.handleJWTException(exception);

        // Then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(errorMessage, responseEntity.getBody());
    }
}
