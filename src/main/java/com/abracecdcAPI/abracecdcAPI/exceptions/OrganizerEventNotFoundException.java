package com.abracecdcAPI.abracecdcAPI.exceptions;

public class OrganizerEventNotFoundException extends RuntimeException{
  public OrganizerEventNotFoundException() {
    super("Organizer of event not found  ");
  }
}
