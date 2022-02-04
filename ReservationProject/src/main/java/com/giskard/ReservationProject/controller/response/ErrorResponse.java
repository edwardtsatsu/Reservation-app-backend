package com.giskard.ReservationProject.controller.response;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String message;

    private final LocalDateTime  timeStamp =  LocalDateTime.now();



}
