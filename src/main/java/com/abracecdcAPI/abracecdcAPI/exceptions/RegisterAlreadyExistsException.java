package com.abracecdcAPI.abracecdcAPI.exceptions;

public class RegisterAlreadyExistsException extends RuntimeException{
    public RegisterAlreadyExistsException() {
        super("Register alredy exists.");
    }
}
