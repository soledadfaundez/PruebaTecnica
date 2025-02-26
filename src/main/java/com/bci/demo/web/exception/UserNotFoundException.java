package com.bci.demo.web.exception;

public class UserNotFoundException extends RuntimeException {
    // Constructor que acepta un mensaje
    public UserNotFoundException(String message) {
        super(message);
    }
}