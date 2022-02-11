package com.giskard.ReservationProject.controller;
import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.exception.InvalidReservationRequestException;
import com.giskard.ReservationProject.request.ReservationRequest;
import com.giskard.ReservationProject.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin
@RestController
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;


    @PostMapping("/reservation")
    public  ResponseEntity<ReservationDto> createReservation(@Valid @RequestBody
                                                             ReservationRequest reservationRequest) throws InvalidReservationRequestException {
        return new ResponseEntity<>(reservationService.createReservation(reservationRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/reservation/{id}/{email}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") UUID id , @PathVariable("email") String email){
        reservationService.deleteReservation(id, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
