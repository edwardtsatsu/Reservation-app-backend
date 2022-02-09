package com.giskard.ReservationProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.exception.InvalidReservationRequestException;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.model.Reservation;
import com.giskard.ReservationProject.repository.AvailabilityRepository;
import com.giskard.ReservationProject.repository.ReservationRepository;
import com.giskard.ReservationProject.request.ReservationRequest;
import com.giskard.ReservationProject.service.ReservationService;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ReservationController.class})
@ExtendWith(SpringExtension.class)
class ReservationControllerTest {
    @Autowired
    private ReservationController reservationController;

    @MockBean
    private ReservationService reservationService;

    @Test
    void testCreateReservation() throws InvalidReservationRequestException {
        Reservation reservation = new Reservation();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        reservation.setDate(fromResult);
        reservation.setEmail("jane.doe@example.org");
        reservation.setEndTime(LocalTime.of(1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        reservation.setId(randomUUIDResult);
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("Dr");
        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        when(reservationRepository.save((Reservation) any())).thenReturn(reservation);

        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any()))
                .thenReturn(Optional.of(availability));
        ReservationController reservationController = new ReservationController(
                new ReservationService(reservationRepository, availabilityRepository));
        ReservationRequest reservationRequest = mock(ReservationRequest.class);
        when(reservationRequest.getEmail()).thenReturn("jane.doe@example.org");
        when(reservationRequest.getTitle()).thenReturn("Dr");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(reservationRequest.getDate()).thenReturn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(reservationRequest.getAvailabilityId()).thenReturn(UUID.randomUUID());
        ResponseEntity<ReservationDto> actualCreateReservationResult = reservationController
                .createReservation(reservationRequest);
        assertTrue(actualCreateReservationResult.getHeaders().isEmpty());
        assertTrue(actualCreateReservationResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualCreateReservationResult.getStatusCode());
        ReservationDto body = actualCreateReservationResult.getBody();
        assertEquals("01:01", body.getEndTime().toString());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertSame(fromResult, body.getDate());
        assertEquals("Dr", body.getTitle());
        assertSame(randomUUIDResult, body.getId());
        assertEquals("01:01", body.getStartTime().toString());
        verify(reservationRepository).save((Reservation) any());
        verify(availabilityRepository).findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any());
        verify(reservationRequest).getEmail();
        verify(reservationRequest).getTitle();
        verify(reservationRequest, atLeast(1)).getDate();
        verify(reservationRequest).getAvailabilityId();
    }

    @Test
    void testCreateReservation2() throws InvalidReservationRequestException {
        ReservationService reservationService = mock(ReservationService.class);
        when(reservationService.createReservation((ReservationRequest) any())).thenReturn(null);
        ReservationController reservationController = new ReservationController(reservationService);
        ReservationRequest reservationRequest = mock(ReservationRequest.class);
        when(reservationRequest.getEmail()).thenReturn("jane.doe@example.org");
        when(reservationRequest.getTitle()).thenReturn("Dr");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(reservationRequest.getDate()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        when(reservationRequest.getAvailabilityId()).thenReturn(UUID.randomUUID());
        ResponseEntity<ReservationDto> actualCreateReservationResult = reservationController
                .createReservation(reservationRequest);
        assertNull(actualCreateReservationResult.getBody());
        assertEquals("<201 CREATED Created,[]>", actualCreateReservationResult.toString());
        assertEquals(HttpStatus.CREATED, actualCreateReservationResult.getStatusCode());
        assertTrue(actualCreateReservationResult.getHeaders().isEmpty());
        verify(reservationService).createReservation((ReservationRequest) any());
    }

    @Test
    void testCreateReservation3() throws InvalidReservationRequestException {
        Reservation reservation = new Reservation();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        reservation.setDate(fromResult);
        reservation.setEmail("jane.doe@example.org");
        reservation.setEndTime(LocalTime.of(1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        reservation.setId(randomUUIDResult);
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("Dr");
        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        when(reservationRepository.save((Reservation) any())).thenReturn(reservation);

        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any()))
                .thenReturn(Optional.of(availability));
        ReservationController reservationController = new ReservationController(
                new ReservationService(reservationRepository, availabilityRepository));
        ReservationRequest reservationRequest = mock(ReservationRequest.class);
        when(reservationRequest.getEmail()).thenReturn("jane.doe@example.org");
        when(reservationRequest.getTitle()).thenReturn("Dr");
        when(reservationRequest.getDate()).thenReturn(null);
        when(reservationRequest.getAvailabilityId()).thenReturn(UUID.randomUUID());
        ResponseEntity<ReservationDto> actualCreateReservationResult = reservationController
                .createReservation(reservationRequest);
        assertTrue(actualCreateReservationResult.getHeaders().isEmpty());
        assertTrue(actualCreateReservationResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualCreateReservationResult.getStatusCode());
        ReservationDto body = actualCreateReservationResult.getBody();
        assertEquals("01:01", body.getEndTime().toString());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertSame(fromResult, body.getDate());
        assertEquals("Dr", body.getTitle());
        assertSame(randomUUIDResult, body.getId());
        assertEquals("01:01", body.getStartTime().toString());
        verify(reservationRepository).save((Reservation) any());
        verify(availabilityRepository).findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any());
        verify(reservationRequest).getEmail();
        verify(reservationRequest).getTitle();
        verify(reservationRequest, atLeast(1)).getDate();
        verify(reservationRequest).getAvailabilityId();
    }

    @Test
    void testDeleteReservation() throws Exception {
        doNothing().when(this.reservationService).deleteReservation((UUID) any(), (String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/reservation/{id}/{email}",
                UUID.randomUUID(), "jane.doe@example.org");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.reservationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

