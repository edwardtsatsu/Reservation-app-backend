package com.giskard.ReservationProject.controller;
import com.giskard.ReservationProject.dto.AvailabilityDto;
import com.giskard.ReservationProject.request.AvailabilityRequest;
import com.giskard.ReservationProject.service.AvailabilitiesService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
public class Controller {

    private final AvailabilitiesService availabilitiesService;


    @PostMapping("/availabilities")
    public ResponseEntity<AvailabilityDto> addAvailabilities(@Valid @RequestBody
                          AvailabilityRequest availabilityRequest) {
        return new ResponseEntity<>(availabilitiesService.addAvailabilities(availabilityRequest), HttpStatus.CREATED);
    }


    @GetMapping("/availabilities")
    public List<AvailabilityDto> getAvailabilities(){
        return availabilitiesService.getAvailabilities();
    }


    @DeleteMapping("/availabilities/{id}")
    public ResponseEntity<?> deleteAvailabilities(@PathVariable("id") Long id){
        availabilitiesService.deleteAvailabilities(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
