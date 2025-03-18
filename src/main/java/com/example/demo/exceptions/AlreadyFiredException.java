package com.example.demo.exceptions;

public class AlreadyFiredException extends RuntimeException {
    public AlreadyFiredException(String message) {
        super(message);
    }
}
