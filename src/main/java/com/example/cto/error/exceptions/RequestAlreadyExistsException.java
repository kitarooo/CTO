package com.example.cto.error.exceptions;

public class RequestAlreadyExistsException extends RuntimeException{
    public RequestAlreadyExistsException(String message) {
        super(message);
    }
}
