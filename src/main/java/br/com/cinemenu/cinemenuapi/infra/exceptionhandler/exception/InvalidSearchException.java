package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

public class InvalidSearchException extends RuntimeException{
    public InvalidSearchException(String message) {
        super(message);
    }
}
