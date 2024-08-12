package com.abracecdcAPI.abracecdcAPI.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User not found.");
    }
}
