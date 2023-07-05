package br.com.cinemenu.cinemenuapi.infra.exceptionhandler;

import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.TMDBNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Objects;

@RestControllerAdvice
public class InternalExceptionHandler {

    @ExceptionHandler(InvalidSearchException.class)
    public ResponseEntity<Object> handleInvalidSearchException(InvalidSearchException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<Object> handleInvalidApiKeyException(InvalidApiKeyException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(new ExceptionValidation(String.valueOf(Objects.requireNonNull(ex.getFieldError())), ex.getMessage(), OffsetDateTime.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleInvalidArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TMDBNotFoundException.class)
    public ResponseEntity<Object> notFoundResponseFromTMDBApi(TMDBNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    public record ExceptionValidation(String field, String message, OffsetDateTime timestamp) {
        public ExceptionValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage(), OffsetDateTime.now());
        }
    }
}
