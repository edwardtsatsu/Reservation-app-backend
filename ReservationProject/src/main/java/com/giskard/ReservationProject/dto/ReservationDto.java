package com.giskard.ReservationProject.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
@Data
@Jacksonized
public class ReservationDto {

    UUID id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime end;


    String title;

    String email;



}
