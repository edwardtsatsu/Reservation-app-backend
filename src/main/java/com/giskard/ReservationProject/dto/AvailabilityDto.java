package com.giskard.ReservationProject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.giskard.ReservationProject.constant.Day;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
