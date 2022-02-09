package com.giskard.ReservationProject.exception;
import com.giskard.ReservationProject.controller.response.ErrorResponse;
import com.giskard.ReservationProject.controller.response.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@RestControllerAdvice
public class GlobalHandler{

    @ExceptionHandler(AvailabilityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAvailabilityNotFoundException(AvailabilityNotFoundException availabilityNotFoundException,
                                                              WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(availabilityNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid1(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> map = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> map.put(error.getField(),error.getDefaultMessage()));
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setErrors(map);
        System.out.println(map);
        return new ResponseEntity<>(validationErrorResponse,HttpStatus.BAD_REQUEST);
    }


}
