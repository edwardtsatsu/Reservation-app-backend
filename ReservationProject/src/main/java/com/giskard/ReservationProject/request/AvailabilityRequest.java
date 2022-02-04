package com.giskard.ReservationProject.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
@Builder
@Data
public class AvailabilityRequest {

    @NotNull
    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date start;


    @NotNull
    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date end;

}
