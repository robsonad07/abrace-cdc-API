package com.abracecdcAPI.abracecdcAPI.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
  public EmailAlreadyExistsException() {
    super("Email alredy exists.");
  }
}
