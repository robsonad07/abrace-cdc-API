package com.abracecdcAPI.abracecdcAPI.exceptions;

public class ValueOfDonationEventIsNegativeException extends RuntimeException {
    public ValueOfDonationEventIsNegativeException() {
        super("Value of Donation Event is negative");
    }
}
