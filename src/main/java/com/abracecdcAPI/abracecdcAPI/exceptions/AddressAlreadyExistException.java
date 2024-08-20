package com.abracecdcAPI.abracecdcAPI.exceptions;

public class AddressAlreadyExistException extends RuntimeException {
    public AddressAlreadyExistException() {
        super("Address alredy exists.");
    }
}
