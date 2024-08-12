package com.abracecdcAPI.abracecdcAPI.exceptions;

public class OrganizerNotFoundException extends RuntimeException{
  public OrganizerNotFoundException() {
    super("Organizer of event not found.");
  }
}
