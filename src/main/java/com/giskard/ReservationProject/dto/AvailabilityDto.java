package com.giskard.ReservationProject.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;


@Value
@Jacksonized
@Builder
@Data
public class AvailabilityDto {

    UUID id;

    int slot;


    String startTime;


    String endTime;

    String day;


}
