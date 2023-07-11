package br.com.cinemenu.cinemenuapi.infra.exceptionhandler;

import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.JWTCineMenuException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.TMDBNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
    @DisplayName("Test handling MethodArgumentNotValidException")
    void handleMethodArgumentNotValidException() {
        // Given
        String fieldName = "name";
        String errorMessage = "Field 'name' is required";
        LocalDate date = LocalDate.of(2023, 5, 25);
        LocalTime time = LocalTime.of(21, 36);
        ZoneOffset offset = ZoneOffset.ofHours(-3);

        OffsetDateTime timestamp = OffsetDateTime.of(date, time, offset);
        FieldError fieldError = new FieldError("object", fieldName, errorMessage);
        BindingResult bindingResult = new org.springframework.validation.BeanPropertyBindingResult(null, null);
        bindingResult.addError(fieldError);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException((MethodParameter) null, bindingResult) {
            @Override
            public String getMessage() {
                return errorMessage;
            }
        };
        InternalExceptionHandler handler = new InternalExceptionHandler();

        // When
        ResponseEntity responseEntity = handler.handleMethodArgumentNotValidException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        InternalExceptionHandler.ExceptionValidation validation = (InternalExceptionHandler.ExceptionValidation) responseEntity.getBody();
        assertEquals(fieldName, validation.field());
        assertEquals(errorMessage, validation.message());
        assertEquals(OffsetDateTime.parse("2023-05-25T21:36-03:00"), timestamp);
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
