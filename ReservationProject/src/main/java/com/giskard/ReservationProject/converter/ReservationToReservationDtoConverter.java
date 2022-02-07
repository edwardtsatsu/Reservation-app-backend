package com.giskard.ReservationProject.converter;
import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.model.Reservation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationToReservationDtoConverter implements Converter<Reservation, ReservationDto> {

    @Override
    public ReservationDto convert(Reservation source) {
        return ReservationDto.builder()
                .start(source.getStart())
                .end(source.getEnd())
                .build();
    }

}
