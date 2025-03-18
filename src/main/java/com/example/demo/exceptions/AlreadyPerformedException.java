package com.example.demo.exceptions;

public class AlreadyPerformedException extends RuntimeException {
    public AlreadyPerformedException(String message) {
        super(message);
    }
}
