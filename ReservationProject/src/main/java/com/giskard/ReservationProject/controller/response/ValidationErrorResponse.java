package com.giskard.ReservationProject.controller.response;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ValidationErrorResponse {
    private Map<String, String> errors;

    private final LocalDateTime timeStamp =  LocalDateTime.now();

}
