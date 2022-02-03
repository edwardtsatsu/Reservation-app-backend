package com.giskard.ReservationProject.dto;
import lombok.Builder;
import lombok.Value;
import java.util.Date;

@Value
@Builder
public class AvailabilitiesDto {
    Date start;

    Date end;
}
