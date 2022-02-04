package com.giskard.ReservationProject.service;
import com.giskard.ReservationProject.converter.AvailabilitiesToAvailabilitiesDtoConverter;
import com.giskard.ReservationProject.model.Availabilities;
import com.giskard.ReservationProject.repository.AvailabilitiesRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AvailabilitiesService {

    private final AvailabilitiesRepository availabilitiesRepository;


    public AvailabilitiesService(AvailabilitiesRepository availabilitiesRepository, AvailabilitiesToAvailabilitiesDtoConverter availabilitiesToAvailabilitiesDtoConverter) {
        this.availabilitiesRepository = availabilitiesRepository;
    }

    public Availabilities addAvailabilities(Availabilities availabilities) {
        availabilities.setUserId(UUID.randomUUID().toString());
        return availabilitiesRepository.save(availabilities);
    }


    public void deleteAvailabilities(Long id) {
        availabilitiesRepository.deleteById(id);
    }


}
