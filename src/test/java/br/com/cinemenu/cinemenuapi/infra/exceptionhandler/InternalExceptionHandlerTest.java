package br.com.cinemenu.cinemenuapi.infra.exceptionhandler;

import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
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
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(errorMessage, responseEntity.getBody());
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
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals(errorMessage, responseEntity.getBody());
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
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        InternalExceptionHandler.ExceptionValidation validation = (InternalExceptionHandler.ExceptionValidation) responseEntity.getBody();
        Assertions.assertEquals(fieldName, validation.field());
        Assertions.assertEquals(errorMessage, validation.message());
        Assertions.assertEquals(OffsetDateTime.parse("2023-05-25T21:36-03:00"), timestamp);
    }
}
