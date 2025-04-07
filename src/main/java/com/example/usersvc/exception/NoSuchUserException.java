package com.example.usersvc.exception;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException() {
        super("No such user with the given ID!");
    }
}
