package br.com.cinemenu.cinemenuapi.infra.exceptionhandler;

import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Generated
public class InternalExceptionHandler {

    @ExceptionHandler(InvalidSearchException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidSearchException(InvalidSearchException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), null, List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidApiKeyException(InvalidApiKeyException ex) {
        return ResponseEntity.internalServerError().body(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), null, List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @ExceptionHandler(TMDBNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundResponseFromTMDBApi(TMDBNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(HttpStatus.NOT_FOUND.value(), null, List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @ExceptionHandler(JWTCineMenuException.class)
    public ResponseEntity<ExceptionResponse> handleJWTException(JWTCineMenuException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), null, List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorsMessages = new ArrayList<>();
        ex.getFieldErrors().forEach(message -> {
            String errorMessage = message.getField() + ": " + message.getDefaultMessage();
            errorsMessages.add(errorMessage);
        });
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getBody().getStatus(), String.valueOf(ex.getFieldErrors().size()), errorsMessages, OffsetDateTime.now()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getBody().getStatus(), ex.getParameterName(), List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), null, List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ExceptionResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ExceptionResponse(HttpStatus.NOT_IMPLEMENTED.value(), ex.getStatusText(), List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @ExceptionHandler(CineMenuEntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCineMenuEntityNotFoundException(CineMenuEntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(HttpStatus.NOT_FOUND.value(), null, List.of(ex.getMessage()), OffsetDateTime.now()));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record ExceptionResponse(Integer status, String fields, List<String> messages, OffsetDateTime dateTime){}

}
