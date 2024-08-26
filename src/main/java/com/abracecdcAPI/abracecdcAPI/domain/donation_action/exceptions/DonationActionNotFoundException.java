package com.abracecdcAPI.abracecdcAPI.domain.donation_action.exceptions;

public class DonationActionNotFoundException extends RuntimeException {
  public DonationActionNotFoundException() {
    super("Donation Action not found");
  }
}
