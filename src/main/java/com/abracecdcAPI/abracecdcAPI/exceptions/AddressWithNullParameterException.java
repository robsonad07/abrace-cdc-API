package com.abracecdcAPI.abracecdcAPI.exceptions;

public class AddressWithNullParameterException extends RuntimeException {
    public AddressWithNullParameterException() {
        super("Address with null parameter.");
    }
}
