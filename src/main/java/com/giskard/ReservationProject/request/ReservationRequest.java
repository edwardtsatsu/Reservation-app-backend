package com.giskard.ReservationProject.request;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;


@Data
@Builder
public class  ReservationRequest {
    @NotNull
    UUID availabilityId;

    @NotNull
    private Date date;
    @NotEmpty
    private String title;
    @NotEmpty
    private String email;


}
