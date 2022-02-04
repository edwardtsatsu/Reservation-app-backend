package com.giskard.ReservationProject.repository;
import com.giskard.ReservationProject.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AvailabilitiesRepository extends JpaRepository<Availability,Long> {

    Optional<Availability> findAvailabilitiesById(Long id);

}
