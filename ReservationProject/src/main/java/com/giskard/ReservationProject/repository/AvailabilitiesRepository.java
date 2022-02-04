package com.giskard.ReservationProject.repository;
import com.giskard.ReservationProject.model.Availabilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AvailabilitiesRepository extends JpaRepository<Availabilities,Long> {

    Optional<Availabilities> findAvailabilitiesById(Long id);

}
