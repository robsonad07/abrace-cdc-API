package com.abracecdcAPI.abracecdcAPI.domain.register_action.exceptions;

public class RegisterActionNotFoundException extends RuntimeException {
  public RegisterActionNotFoundException() {
    super("Register not found");
  }
}
