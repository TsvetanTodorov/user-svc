package com.example.usersvc.exception;

public class UserUpdateException extends RuntimeException {
    public UserUpdateException() {
        super("Please enter at least 1 valid field.");
    }
}
