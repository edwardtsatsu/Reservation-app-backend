package com.giskard.ReservationProject.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "AVAILABILITIES")
@Accessors(chain = true)
public class Availability {
    @OneToMany(mappedBy = "availability", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservation;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "_start")
    private LocalDateTime start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "_end")
    private LocalDateTime end;



}
