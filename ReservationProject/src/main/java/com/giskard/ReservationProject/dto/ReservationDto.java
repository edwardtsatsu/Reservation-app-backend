package com.giskard.ReservationProject.dto;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.Date;

@Value
@Builder
@Data
public class ReservationDto {
    Date start;

    Date end;

}
