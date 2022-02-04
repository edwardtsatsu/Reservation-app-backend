package com.giskard.ReservationProject.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.persistence.SecondaryTable;
import java.util.Date;

@Value
@Builder
@Data
public class AvailabilitiesDto {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date end;
}
