package com.giskard.ReservationProject.dto;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import java.time.LocalDateTime;


@Value
@Builder
@Data
public class ReservationDto {

    Long id;

    LocalDateTime start;

    LocalDateTime end;

    String title;

    String email;



}
