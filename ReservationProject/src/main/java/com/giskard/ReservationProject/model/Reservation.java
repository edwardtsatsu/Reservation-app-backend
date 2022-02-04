package com.giskard.ReservationProject.model;
import lombok.*;
import javax.persistence.*;
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

    @Column(name = "start")
    private Date start;
    @Column(name = "_end")
    private Date end;
    @Column
    private String title;
    @Column
    private String email;


}
