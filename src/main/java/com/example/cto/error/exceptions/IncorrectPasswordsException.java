package com.example.cto.error.exceptions;

public class IncorrectPasswordsException extends RuntimeException{
    public IncorrectPasswordsException(String message) {
        super(message);
    }
}
