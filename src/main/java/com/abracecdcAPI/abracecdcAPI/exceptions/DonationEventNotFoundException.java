package com.abracecdcAPI.abracecdcAPI.exceptions;

public class DonationEventNotFoundException extends RuntimeException {
    public DonationEventNotFoundException(){
        super("Donation Event not found.");
    }
}
