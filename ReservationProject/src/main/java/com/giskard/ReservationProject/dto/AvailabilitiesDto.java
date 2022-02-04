package com.giskard.ReservationProject.dto;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.persistence.SecondaryTable;
import java.util.Date;

@Value
@Builder
@Data
public class AvailabilitiesDto {
    Date start;

    Date end;
}
