package com.example.demo.exceptions;

public class AlreadyBorrowedBookException extends RuntimeException {
    public AlreadyBorrowedBookException(String message) {
        super(message);
    }
}
