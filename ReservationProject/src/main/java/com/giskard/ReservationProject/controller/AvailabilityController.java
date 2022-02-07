package com.giskard.ReservationProject.controller;
import com.giskard.ReservationProject.dto.AvailabilityDto;
import com.giskard.ReservationProject.request.AvailabilityRequest;
import com.giskard.ReservationProject.service.AvailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@AllArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilitiesService;


    @PostMapping("/availabilities")
    public ResponseEntity<AvailabilityDto> createAvailabilities(@Valid @RequestBody
                          AvailabilityRequest availabilityRequest) {
        return new ResponseEntity<>(availabilitiesService.createAvailabilities(availabilityRequest), HttpStatus.CREATED);
    }


    @GetMapping("/availabilities")
    public List<AvailabilityDto> getAvailabilities(){
        return availabilitiesService.getAvailabilities();
    }


    @DeleteMapping("/availabilities/{id}")
    public ResponseEntity<?> deleteAvailabilities(@PathVariable("id") UUID id){
        availabilitiesService.deleteAvailabilities(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
