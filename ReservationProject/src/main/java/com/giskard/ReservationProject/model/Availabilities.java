package com.giskard.ReservationProject.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "AVAILABILITIES")
public class Availabilities implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end;


}
