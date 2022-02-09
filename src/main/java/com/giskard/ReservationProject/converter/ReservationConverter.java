package com.giskard.ReservationProject.converter;
import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.model.Reservation;
import com.giskard.ReservationProject.request.ReservationRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


public class ReservationConverter  {

    @NonNull
    public static Reservation convertToEntity(ReservationRequest source, Availability availability) {
        return Reservation.builder()
                .startTime(availability.getStartTime())
                .endTime(availability.getEndTime())
                .title(source.getTitle())
                .email(source.getEmail())
                .date(source.getDate())
                .build();
    }


    @NonNull
    public static ReservationDto convertToDto(Reservation source) {
        return ReservationDto.builder()
                .startTime(source.getStartTime())
                .endTime(source.getEndTime())
                .id(source.getId())
                .title(source.getTitle())
                .email(source.getEmail())
                .date(source.getDate())
                .build();
    }

}
