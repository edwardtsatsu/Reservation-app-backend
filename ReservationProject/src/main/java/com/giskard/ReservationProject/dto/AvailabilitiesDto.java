package com.giskard.ReservationProject.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Value
@Builder
@Data
public class AvailabilitiesDto {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date end;

    UUID id;

}
