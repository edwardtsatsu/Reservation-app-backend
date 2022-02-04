package com.giskard.ReservationProject.service;
import com.giskard.ReservationProject.converter.AvailabilityRequestToAvailabilityConverter;
import com.giskard.ReservationProject.dto.AvailabilityDto;
import com.giskard.ReservationProject.converter.AvailabilityToAvailabilityDtoConverter;
import com.giskard.ReservationProject.exception.AvailabilityNotFoundException;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.repository.AvailabilitiesRepository;
import com.giskard.ReservationProject.request.AvailabilityRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvailabilitiesService {


    private final AvailabilitiesRepository availabilitiesRepository;
    private final AvailabilityToAvailabilityDtoConverter availabilitiesToAvailabilitiesResponseConverter;
    private final AvailabilityRequestToAvailabilityConverter availabilityRequestToAvailabilityConverter;


    public AvailabilityDto addAvailabilities(AvailabilityRequest availabilityRequest) {
        return availabilitiesToAvailabilitiesResponseConverter
                .convert(availabilitiesRepository.save(availabilityRequestToAvailabilityConverter
                        .convert(availabilityRequest)));
    }


    public void deleteAvailabilities(Long id) {
        Availability availabilities = availabilitiesRepository.
                findById(id).orElseThrow(()-> new AvailabilityNotFoundException("Availability with id " + id + " not found"));
        availabilitiesRepository.delete(availabilities);
    }


    public List<AvailabilityDto> getAvailabilities() {
        return availabilitiesRepository.findAll().stream()
                .map(availabilitiesToAvailabilitiesResponseConverter::convert)
                .collect(Collectors.toList());
    }


}
