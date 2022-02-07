package com.giskard.ReservationProject.converter;
import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.model.Reservation;
import com.giskard.ReservationProject.request.ReservationRequest;
import org.springframework.lang.NonNull;


public class ReservationConverter  {

    @NonNull
    public static Reservation convertToEntity(ReservationRequest source , Availability availability) {
        return Reservation.builder()
                .title(source.getTitle())
                .start(source.getStart())
                .end(source.getEnd())
                .availability(availability)
                .email(source.getEmail())
                .build();
    }


    @NonNull
    public static ReservationDto convertToDto(Reservation source) {
        return ReservationDto.builder()
                .start(source.getStart())
                .id(source.getId())
                .end(source.getEnd())
                .email(source.getEmail())
                .title(source.getTitle())
                .build();
    }

}
