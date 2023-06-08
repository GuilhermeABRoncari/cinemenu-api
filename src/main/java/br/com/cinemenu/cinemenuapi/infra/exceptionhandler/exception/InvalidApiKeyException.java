package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

public class InvalidApiKeyException extends RuntimeException {
    public InvalidApiKeyException(String message) {
        super(message);
    }
}
