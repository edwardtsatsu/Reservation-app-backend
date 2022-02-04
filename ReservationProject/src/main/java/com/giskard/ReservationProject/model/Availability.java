package com.giskard.ReservationProject.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "AVAILABILITIES")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "_start")
    private LocalDateTime start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "_end")
    private LocalDateTime end;



}
