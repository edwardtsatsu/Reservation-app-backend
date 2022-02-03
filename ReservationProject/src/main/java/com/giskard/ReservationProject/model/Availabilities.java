package com.giskard.ReservationProject.model;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Availabilities {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date start;
    private Date end;


}
