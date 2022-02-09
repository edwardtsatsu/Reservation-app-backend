package com.giskard.ReservationProject.converter;
import com.giskard.ReservationProject.dto.AvailabilityDto;
import com.giskard.ReservationProject.model.Availability;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityToAvailabilityDtoConverter implements Converter<Availability, AvailabilityDto> {


    @Override
    public AvailabilityDto convert(Availability source) {
        return AvailabilityDto.builder()
                .startTime(source.getStartTime().toString())
                .id(source.getId())
                .slot(source.getSlot())
                .day(source.getDay())
                .endTime(source.getEndTime().toString())
                .build();
    }

}
