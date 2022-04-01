package com.giskard.ReservationProject.service;
import com.giskard.ReservationProject.converter.ReservationConverter;
import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.exception.AvailabilityNotFoundException;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.model.Reservation;
import com.giskard.ReservationProject.repository.AvailabilityRepository;
import com.giskard.ReservationProject.repository.ReservationRepository;
import com.giskard.ReservationProject.request.ReservationRequest;
import com.giskard.ReservationProject.utils.Helpers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AvailabilityRepository availabilityRepository;


    public ReservationDto createReservation(ReservationRequest reservationRequest)  {
        Availability availability = availabilityRepository.findByIdAndDayAndSlotGreaterThan(reservationRequest.getAvailabilityId(), Helpers.getDayFromDate(reservationRequest.getDate()),0)
                .orElseThrow(()-> new AvailabilityNotFoundException("Availability with id " + reservationRequest.getAvailabilityId() + " not found"));
        Reservation reservation = ReservationConverter.convertToEntity(reservationRequest,availability);
        availability.setSlot(availability.getSlot() -1);
        return ReservationConverter.convertToDto(reservationRepository.save(reservation));

    }


    public void deleteReservation(UUID id, String email) {
        Reservation reservation = reservationRepository.
                findByIdAndEmail(id, email).orElseThrow(()->
                        new AvailabilityNotFoundException("Reservation with id " + id + "and email " + email + " not found"));
        System.out.println("reservation");
        reservationRepository.delete(reservation);
    }


    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationConverter::convertToDto)
                .collect(Collectors.toList());
    }


}
