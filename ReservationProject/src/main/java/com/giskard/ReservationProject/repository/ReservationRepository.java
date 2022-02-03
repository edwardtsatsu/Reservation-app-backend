package com.giskard.ReservationProject.repository;
import com.giskard.ReservationProject.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Optional<Reservation> findReservationById(Long id);
}
