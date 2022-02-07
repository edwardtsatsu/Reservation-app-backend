package com.giskard.ReservationProject.service;
import com.giskard.ReservationProject.converter.ReservationConverter;
import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.exception.InvalidReservationRequestException;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.repository.AvailabilityRepository;
import com.giskard.ReservationProject.repository.ReservationRepository;
import com.giskard.ReservationProject.request.ReservationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AvailabilityRepository availabilityRepository;


    public ReservationDto createReservation(ReservationRequest reservationRequest) throws InvalidReservationRequestException {
        Availability availabilityObject = availabilityRepository.findById(reservationRequest.getAvailabilityId()).get();
        if (validateReservationRequest(reservationRequest, availabilityObject))
            return ReservationConverter
                    .convertToDto(reservationRepository.save(ReservationConverter.convertToEntity(reservationRequest, availabilityObject)));
        throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,"Timeslot is not available!");
    }

    private boolean validateReservationRequest(ReservationRequest reservationRequest, Availability availability) {
        return isRequestWithinAvailabilityPeriod(reservationRequest.getStart().isAfter(availability.getStart()), reservationRequest.getEnd().isBefore(availability.getEnd())) ||
                isRequestWithinAvailabilityPeriod(reservationRequest.getStart().isEqual(availability.getStart()), reservationRequest.getEnd().isEqual(availability.getEnd())) ||
                isRequestWithinAvailabilityPeriod(reservationRequest.getStart().isEqual(availability.getStart()), reservationRequest.getEnd().isBefore(availability.getEnd())) ||
                isRequestWithinAvailabilityPeriod(reservationRequest.getStart().isAfter(availability.getStart()), reservationRequest.getEnd().isEqual(availability.getEnd()))
                ;
    }

    private boolean isRequestWithinAvailabilityPeriod(boolean reservationRequest, boolean reservationRequest1) {
        return reservationRequest &
                reservationRequest1;
    }


}
