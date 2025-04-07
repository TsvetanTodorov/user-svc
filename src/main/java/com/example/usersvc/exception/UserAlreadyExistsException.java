package com.example.usersvc.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("User with the given email or phone number already exists.");
    }


}
