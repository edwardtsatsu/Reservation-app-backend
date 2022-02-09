package com.giskard.ReservationProject.service;
import com.giskard.ReservationProject.converter.AvailabilityRequestToAvailabilityConverter;
import com.giskard.ReservationProject.dto.AvailabilityDto;
import com.giskard.ReservationProject.converter.AvailabilityToAvailabilityDtoConverter;
import com.giskard.ReservationProject.exception.AvailabilityNotFoundException;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.repository.AvailabilityRepository;
import com.giskard.ReservationProject.request.AvailabilityRequest;
import com.giskard.ReservationProject.utils.Helpers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter;
    private final AvailabilityRequestToAvailabilityConverter availabilityRequestToAvailabilityConverter;


    public AvailabilityDto createAvailabilities(AvailabilityRequest availabilityRequest) {
        return availabilityToAvailabilityDtoConverter
                .convert(availabilityRepository.save(availabilityRequestToAvailabilityConverter
                        .convert(availabilityRequest)));
    }


    public void deleteAvailabilities(UUID id) {
        Availability availabilities = availabilityRepository.
                findById(id).orElseThrow(()->
                        new AvailabilityNotFoundException("Availability with id " + id + " not found"));
        availabilityRepository.delete(availabilities);
    }


    public List<AvailabilityDto> getAvailabilities(Date date) {
        List<Availability> availabilities = date == null
                ? availabilityRepository.findAll()
                :availabilityRepository.findByDayAndSlotGreaterThan(Helpers.getDayFromDate(date),0);

        return  availabilities.stream()
                .map(availabilityToAvailabilityDtoConverter::convert)
                .collect(Collectors.toList());
    }




}
