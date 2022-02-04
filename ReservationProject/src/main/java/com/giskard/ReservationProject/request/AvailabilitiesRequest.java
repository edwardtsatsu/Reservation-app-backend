package com.giskard.ReservationProject.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import java.io.Serializable;
import java.util.Date;

@Value
@Builder
@Data
public class AvailabilitiesRequest {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date end;
}
