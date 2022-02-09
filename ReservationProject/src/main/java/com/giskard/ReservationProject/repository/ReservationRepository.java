package com.giskard.ReservationProject.repository;
import com.giskard.ReservationProject.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

   Optional<Reservation> findByIdAndEmail(UUID id, String email);


}
