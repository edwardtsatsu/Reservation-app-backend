package com.giskard.ReservationProject.converter;
import com.giskard.ReservationProject.dto.AvailabilitiesDto;
import com.giskard.ReservationProject.model.Availabilities;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvailabilitiesToAvailabilitiesDtoConverter implements Converter<Availabilities, AvailabilitiesDto> {

    @Override
    public AvailabilitiesDto convert(Availabilities source) {
        return AvailabilitiesDto.builder()
                .start(source.getStart())
                .end(source.getEnd())
                .build();
    }

}
