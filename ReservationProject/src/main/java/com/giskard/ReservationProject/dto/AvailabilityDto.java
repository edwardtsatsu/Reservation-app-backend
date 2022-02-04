package com.giskard.ReservationProject.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.util.Date;

@Value
@Jacksonized
@Builder
@Data
public class AvailabilityDto {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date end;

    Long id;

}
