package com.giskard.ReservationProject.repository;
import com.giskard.ReservationProject.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

}
