package com.abracecdcAPI.abracecdcAPI.exceptions;

public class CellphoneAlreadyExistsException extends RuntimeException {
  public CellphoneAlreadyExistsException() {
    super("Cellphone alredy exists");
  }
}
