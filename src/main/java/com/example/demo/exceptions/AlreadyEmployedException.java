package com.example.demo.exceptions;

public class AlreadyEmployedException extends RuntimeException{
    public AlreadyEmployedException(String message) {
        super(message);
    }
}
