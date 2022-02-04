package com.giskard.ReservationProject.service;
import com.giskard.ReservationProject.model.Availabilities;
import com.giskard.ReservationProject.repository.AvailabilitiesRepository;
import org.springframework.stereotype.Service;

@Service
public class AvailabilitiesService {

    private final AvailabilitiesRepository availabilitiesRepository;

    public AvailabilitiesService(AvailabilitiesRepository availabilitiesRepository) {
        this.availabilitiesRepository = availabilitiesRepository;
    }

    public Availabilities addAvailabilities(Availabilities availabilities){
        availabilities.setId(availabilities.getId());
        return availabilitiesRepository.save(availabilities);
    }


    public void deleteAvailabilities(Long id){
        availabilitiesRepository.deleteById(id);
    }


}
