package com.giskard.ReservationProject.model;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Entity
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESERVATION")
@Accessors(chain = true)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "_start")
    private LocalDateTime start;

    @Column(name = "_end")
    private LocalDateTime end;


    private String title;


    private String email;


    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;

}
