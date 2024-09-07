package com.abracecdcAPI.abracecdcAPI.exceptions;

public class RegisterAlreadyExistsException extends RuntimeException {
    public RegisterAlreadyExistsException(String urlImage) {
        super("Register with URL image " + urlImage + " already exists.");
    }
}
