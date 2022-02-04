package com.giskard.ReservationProject.controller;
import com.giskard.ReservationProject.model.Availabilities;
import com.giskard.ReservationProject.service.AvailabilitiesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class Controller {

    private final AvailabilitiesService availabilitiesService;

    public Controller(AvailabilitiesService availabilitiesService) {
        this.availabilitiesService = availabilitiesService;
    }


    @PostMapping("/add")
    public ResponseEntity<Availabilities> addAvailabilities(@RequestBody
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Availabilities availabilities) {
        Availabilities newAvailability = availabilitiesService.addAvailabilities(availabilities);
        return new ResponseEntity<>(newAvailability, HttpStatus.CREATED);
    }


}
