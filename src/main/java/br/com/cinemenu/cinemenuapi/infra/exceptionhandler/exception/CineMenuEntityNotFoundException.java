package br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception;

public class CineMenuEntityNotFoundException extends RuntimeException{
    public CineMenuEntityNotFoundException(String message) {
        super(message);
    }
}
