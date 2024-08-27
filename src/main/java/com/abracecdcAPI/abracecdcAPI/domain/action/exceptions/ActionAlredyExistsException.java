package com.abracecdcAPI.abracecdcAPI.domain.action.exceptions;

public class ActionAlredyExistsException extends RuntimeException {
  public ActionAlredyExistsException() {
    super("Action alredy exists");
  }
}
