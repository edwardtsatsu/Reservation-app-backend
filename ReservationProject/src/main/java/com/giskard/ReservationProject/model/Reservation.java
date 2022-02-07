package com.giskard.ReservationProject.model;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESERVATION")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "_start")
    private LocalDateTime start;

    @Column(name = "_end")
    private LocalDateTime end;

    @Column(name = "_title")
    private String title;

    @Column
    private String email;


}
