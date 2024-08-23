package com.abracecdcAPI.abracecdcAPI.exceptions;

public class ActionNotFoundException extends RuntimeException {
  
  public ActionNotFoundException() {
    super("Action not found");
  }
}
