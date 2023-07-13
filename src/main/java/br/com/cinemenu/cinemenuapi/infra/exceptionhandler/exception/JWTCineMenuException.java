package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

public class JWTCineMenuException extends RuntimeException{
    public JWTCineMenuException(String message) {
        super(message);
    }
}
