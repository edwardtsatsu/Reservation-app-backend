package com.giskard.ReservationProject.repository;
import com.giskard.ReservationProject.model.Availabilities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailabilitiesRepository extends JpaRepository<Appendable,Long> {

    Optional<Availabilities> findAvailabilitiesById(Long id);
}
