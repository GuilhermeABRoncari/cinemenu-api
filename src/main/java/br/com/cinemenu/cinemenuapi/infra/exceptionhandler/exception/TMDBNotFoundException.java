package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

public class TMDBNotFoundException extends RuntimeException {
    public TMDBNotFoundException(String message) {
        super(message);
    }
}
