package com.example.demo.exceptions;

public class PatientIsDischargedException extends RuntimeException {
    public PatientIsDischargedException(String message) {
        super(message);
    }
}
