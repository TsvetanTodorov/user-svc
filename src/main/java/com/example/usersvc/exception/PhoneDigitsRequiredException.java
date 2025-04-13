package com.example.usersvc.exception;

public class PhoneDigitsRequiredException extends RuntimeException {

    public PhoneDigitsRequiredException() {
        super("Phone number must be 10 digits.");
    }
}
