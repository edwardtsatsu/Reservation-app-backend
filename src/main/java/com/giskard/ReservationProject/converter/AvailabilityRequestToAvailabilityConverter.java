package com.giskard.ReservationProject.converter;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.request.AvailabilityRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityRequestToAvailabilityConverter implements Converter<AvailabilityRequest, Availability> {

    @Override
    @NonNull
    public Availability convert(AvailabilityRequest source) {
        return Availability.builder()
                .startTime(source.getStartTime())
                .endTime(source.getEndTime())
                .day(source.getDay().toString())
                .slot(source.getSlot())
                .build();
    }
}
