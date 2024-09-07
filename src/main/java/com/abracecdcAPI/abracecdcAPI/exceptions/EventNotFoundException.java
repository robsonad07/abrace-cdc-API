package com.abracecdcAPI.abracecdcAPI.exceptions;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(UUID eventId) {
        super("Event with ID " + eventId + " not found.");
    }

    public EventNotFoundException() {
        super("Event not found.");
    }
}
