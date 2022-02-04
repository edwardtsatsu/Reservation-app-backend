package com.giskard.ReservationProject.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Value
@Jacksonized
@Builder
public class AvailabilitiesResponse {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date end;

}
