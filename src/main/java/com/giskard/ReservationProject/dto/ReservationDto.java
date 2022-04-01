package com.giskard.ReservationProject.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;


@Value
@Builder
@Data
@Jacksonized
public class ReservationDto {

    UUID id;

    LocalTime startTime;

    LocalTime endTime;

    Date date;

    String title;

    String email;


}
