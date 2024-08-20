package com.abracecdcAPI.abracecdcAPI.exceptions;

public class EventAlreadyExistsException extends RuntimeException{
    public EventAlreadyExistsException() {
        super("Event alredy exists.");
    }
}
