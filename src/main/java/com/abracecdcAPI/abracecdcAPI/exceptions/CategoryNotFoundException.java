package com.abracecdcAPI.abracecdcAPI.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(){
        super("Category of event not found.");
    }
}