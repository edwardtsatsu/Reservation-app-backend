package com.giskard.ReservationProject.repository;
import com.giskard.ReservationProject.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {

    List<Availability> findByDayAndSlotGreaterThan(String day, Integer slot );

    Optional<Availability> findByIdAndDayAndSlotGreaterThan(UUID id, String day, Integer slot );

}
