package com.giskard.ReservationProject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.exception.AvailabilityNotFoundException;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.model.Reservation;
import com.giskard.ReservationProject.repository.AvailabilityRepository;
import com.giskard.ReservationProject.repository.ReservationRepository;
import com.giskard.ReservationProject.request.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReservationService.class})
@ExtendWith(SpringExtension.class)
class ReservationServiceTest {
    @MockBean
    private AvailabilityRepository availabilityRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Test
    void testCreateReservation() {
        Reservation reservation = new Reservation();
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        reservation.setDate(fromResult);
        reservation.setEmail("jane.doe@example.org");
        reservation.setEndTime(LocalTime.of(1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        reservation.setId(randomUUIDResult);
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("A tech talk");
        when(this.reservationRepository.save((Reservation) any())).thenReturn(reservation);

        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        Optional<Availability> ofResult = Optional.of(availability);
        when(this.availabilityRepository.findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any()))
                .thenReturn(ofResult);
        ReservationRequest reservationRequest = mock(ReservationRequest.class);
        when(reservationRequest.getEmail()).thenReturn("jane.doe@example.org");
        when(reservationRequest.getTitle()).thenReturn("A tech talk");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 1, 1).atStartOfDay();
        when(reservationRequest.getDate()).thenReturn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(reservationRequest.getAvailabilityId()).thenReturn(UUID.randomUUID());
        ReservationDto actualCreateReservationResult = this.reservationService.createReservation(reservationRequest);
        assertSame(fromResult, actualCreateReservationResult.getDate());
        assertEquals("A tech talk", actualCreateReservationResult.getTitle());
        assertEquals("01:01", actualCreateReservationResult.getStartTime().toString());
        assertSame(randomUUIDResult, actualCreateReservationResult.getId());
        assertEquals("01:01", actualCreateReservationResult.getEndTime().toString());
        assertEquals("jane.doe@example.org", actualCreateReservationResult.getEmail());
        verify(this.reservationRepository).save((Reservation) any());
        verify(this.availabilityRepository).findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any());
        verify(reservationRequest).getEmail();
        verify(reservationRequest).getTitle();
        verify(reservationRequest, atLeast(1)).getDate();
        verify(reservationRequest).getAvailabilityId();
    }

    @Test
    void testCreateReservation2() {
        Reservation reservation = new Reservation();
        LocalDateTime atStartOfDayResult = LocalDate.of(2020, 1, 1).atStartOfDay();
        reservation.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        reservation.setEmail("jane.doe@example.org");
        reservation.setEndTime(LocalTime.of(1, 1));
        reservation.setId(UUID.randomUUID());
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("A brief meeting");
        when(this.reservationRepository.save((Reservation) any())).thenReturn(reservation);

        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        Optional<Availability> ofResult = Optional.of(availability);
        when(this.availabilityRepository.findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any()))
                .thenReturn(ofResult);
        ReservationRequest reservationRequest = mock(ReservationRequest.class);
        when(reservationRequest.getEmail()).thenThrow(new AvailabilityNotFoundException("An error occurred"));
        when(reservationRequest.getTitle()).thenThrow(new AvailabilityNotFoundException("An error occurred"));
        when(reservationRequest.getDate()).thenThrow(new AvailabilityNotFoundException("An error occurred"));
        when(reservationRequest.getAvailabilityId()).thenThrow(new AvailabilityNotFoundException("An error occurred"));
        assertThrows(AvailabilityNotFoundException.class,
                () -> this.reservationService.createReservation(reservationRequest));
        verify(reservationRequest).getAvailabilityId();
    }

    @Test
    void testCreateReservation3() {
        Reservation reservation = new Reservation();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        reservation.setEmail("jane.doe@example.org");
        reservation.setEndTime(LocalTime.of(1, 1));
        reservation.setId(UUID.randomUUID());
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("Create a theme");

        Reservation reservation1 = new Reservation();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        reservation1.setEmail("jane.doe@example.org");
        reservation1.setEndTime(LocalTime.of(1, 1));
        reservation1.setId(UUID.randomUUID());
        reservation1.setStartTime(LocalTime.of(1, 1));
        reservation1.setTitle("Dr");

        Reservation reservation2 = new Reservation();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation2.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        reservation2.setEmail("jane.doe@example.org");
        reservation2.setEndTime(LocalTime.of(1, 1));
        reservation2.setId(UUID.randomUUID());
        reservation2.setStartTime(LocalTime.of(1, 1));
        reservation2.setTitle("Dr");

        Reservation reservation3 = new Reservation();
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation3.setDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        reservation3.setEmail("jane.doe@example.org");
        reservation3.setEndTime(LocalTime.of(1, 1));
        reservation3.setId(UUID.randomUUID());
        reservation3.setStartTime(LocalTime.of(1, 1));
        reservation3.setTitle("Dr");

        Reservation reservation4 = new Reservation();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation4.setDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        reservation4.setEmail("jane.doe@example.org");
        reservation4.setEndTime(LocalTime.of(1, 1));
        reservation4.setId(UUID.randomUUID());
        reservation4.setStartTime(LocalTime.of(1, 1));
        reservation4.setTitle("Dr");

        Reservation reservation5 = new Reservation();
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation5.setDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        reservation5.setEmail("jane.doe@example.org");
        reservation5.setEndTime(LocalTime.of(1, 1));
        reservation5.setId(UUID.randomUUID());
        reservation5.setStartTime(LocalTime.of(1, 1));
        reservation5.setTitle("Dr");
        Reservation reservation6 = mock(Reservation.class);
        when(reservation6.setDate((Date) any())).thenReturn(reservation);
        when(reservation6.setEmail((String) any())).thenReturn(reservation1);
        when(reservation6.setEndTime((LocalTime) any())).thenReturn(reservation2);
        when(reservation6.setId((UUID) any())).thenReturn(reservation3);
        when(reservation6.setStartTime((LocalTime) any())).thenReturn(reservation4);
        when(reservation6.setTitle((String) any())).thenReturn(reservation5);
        when(reservation6.getEmail()).thenReturn("jane.doe@example.org");
        when(reservation6.getTitle()).thenReturn("Dr");
        when(reservation6.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(reservation6.getStartTime()).thenReturn(LocalTime.of(1, 1));
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant());
        when(reservation6.getDate()).thenReturn(fromResult);
        UUID randomUUIDResult = UUID.randomUUID();
        when(reservation6.getId()).thenReturn(randomUUIDResult);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation6.setDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        reservation6.setEmail("jane.doe@example.org");
        reservation6.setEndTime(LocalTime.of(1, 1));
        reservation6.setId(UUID.randomUUID());
        reservation6.setStartTime(LocalTime.of(1, 1));
        reservation6.setTitle("Dr");
        when(this.reservationRepository.save((Reservation) any())).thenReturn(reservation6);

        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        Optional<Availability> ofResult = Optional.of(availability);
        when(this.availabilityRepository.findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any()))
                .thenReturn(ofResult);
        ReservationRequest reservationRequest = mock(ReservationRequest.class);
        when(reservationRequest.getEmail()).thenReturn("jane.doe@example.org");
        when(reservationRequest.getTitle()).thenReturn("Dr");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(reservationRequest.getDate()).thenReturn(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        when(reservationRequest.getAvailabilityId()).thenReturn(UUID.randomUUID());
        ReservationDto actualCreateReservationResult = this.reservationService.createReservation(reservationRequest);
        assertSame(fromResult, actualCreateReservationResult.getDate());
        assertEquals("Dr", actualCreateReservationResult.getTitle());
        assertEquals("01:01", actualCreateReservationResult.getStartTime().toString());
        assertSame(randomUUIDResult, actualCreateReservationResult.getId());
        assertEquals("01:01", actualCreateReservationResult.getEndTime().toString());
        assertEquals("jane.doe@example.org", actualCreateReservationResult.getEmail());
        verify(this.reservationRepository).save((Reservation) any());
        verify(reservation6).setDate((Date) any());
        verify(reservation6).setEmail((String) any());
        verify(reservation6).setEndTime((LocalTime) any());
        verify(reservation6).setId((UUID) any());
        verify(reservation6).setStartTime((LocalTime) any());
        verify(reservation6).setTitle((String) any());
        verify(reservation6).getEmail();
        verify(reservation6).getTitle();
        verify(reservation6).getEndTime();
        verify(reservation6).getStartTime();
        verify(reservation6).getDate();
        verify(reservation6).getId();
        verify(this.availabilityRepository).findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any());
        verify(reservationRequest).getEmail();
        verify(reservationRequest).getTitle();
        verify(reservationRequest, atLeast(1)).getDate();
        verify(reservationRequest).getAvailabilityId();
    }

    @Test
    void testDeleteReservation() {
        Reservation reservation = new Reservation();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        reservation.setEmail("jane.doe@example.org");
        reservation.setEndTime(LocalTime.of(1, 1));
        reservation.setId(UUID.randomUUID());
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("Let us have a talk");
        Optional<Reservation> ofResult = Optional.of(reservation);
        doNothing().when(this.reservationRepository).delete((Reservation) any());
        when(this.reservationRepository.findByIdAndEmail((UUID) any(), (String) any())).thenReturn(ofResult);
        this.reservationService.deleteReservation(UUID.randomUUID(), "jane.doe@example.org");
        verify(this.reservationRepository).findByIdAndEmail((UUID) any(), (String) any());
        verify(this.reservationRepository).delete((Reservation) any());
    }

    @Test
    void testDeleteReservation2() {
        Reservation reservation = new Reservation();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        reservation.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        reservation.setEmail("jane.doe@example.org");
        reservation.setEndTime(LocalTime.of(1, 1));
        reservation.setId(UUID.randomUUID());
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("Dr");
        Optional<Reservation> ofResult = Optional.of(reservation);
        doThrow(new AvailabilityNotFoundException("An error occurred")).when(this.reservationRepository)
                .delete((Reservation) any());
        when(this.reservationRepository.findByIdAndEmail((UUID) any(), (String) any())).thenReturn(ofResult);
        assertThrows(AvailabilityNotFoundException.class,
                () -> this.reservationService.deleteReservation(UUID.randomUUID(), "jane.doe@example.org"));
        verify(this.reservationRepository).findByIdAndEmail((UUID) any(), (String) any());
        verify(this.reservationRepository).delete((Reservation) any());
    }

    @Test
    void testDeleteReservation3() {
        doNothing().when(this.reservationRepository).delete((Reservation) any());
        when(this.reservationRepository.findByIdAndEmail((UUID) any(), (String) any())).thenReturn(Optional.empty());
        assertThrows(AvailabilityNotFoundException.class,
                () -> this.reservationService.deleteReservation(UUID.randomUUID(), "jane.doe@example.org"));
        verify(this.reservationRepository).findByIdAndEmail((UUID) any(), (String) any());
    }
}

