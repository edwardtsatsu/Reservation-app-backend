package com.giskard.ReservationProject.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.giskard.ReservationProject.constant.Day;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;


@Builder
@Data
public class AvailabilityRequest {

    @NotNull
    private Integer slot;

    @NotNull
    @JsonFormat(pattern="HH:mm")
    private LocalTime startTime;

    @NotNull
    @JsonFormat(pattern="HH:mm")
    private LocalTime endTime;

    @NotNull
    private Day day;

}


