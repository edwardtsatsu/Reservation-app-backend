package com.giskard.ReservationProject.exception;

public class InvalidReservationRequestException extends RuntimeException {
    public InvalidReservationRequestException(String message) {
        super(message);
    }
}
