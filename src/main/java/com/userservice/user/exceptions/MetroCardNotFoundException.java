package com.userservice.user.exceptions;

public class MetroCardNotFoundException extends RuntimeException {
    public MetroCardNotFoundException(String message) {
        super(message);
    }
}