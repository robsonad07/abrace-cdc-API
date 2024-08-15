package com.abracecdcAPI.abracecdcAPI.exceptions;

public class RegisterNotFoundException extends RuntimeException{
    public RegisterNotFoundException() {
        super("Register not found.");
    }
}
