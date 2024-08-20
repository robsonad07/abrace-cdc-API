package com.abracecdcAPI.abracecdcAPI.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(){
        super("Address not found.");
    }
}
