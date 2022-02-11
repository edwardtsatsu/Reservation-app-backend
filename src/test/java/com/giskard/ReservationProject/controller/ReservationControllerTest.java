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
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        reservation.setDate(fromResult);
        reservation.setEmail("edwardakorlie73@gmail.com");
        reservation.setEndTime(LocalTime.of(1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        reservation.setId(randomUUIDResult);
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("Quality assurance on Ai");
        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        when(reservationRepository.save((Reservation) any())).thenReturn(reservation);

        Availability availability = new Availability();
        availability.setDay("MONDAY");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(5);
        availability.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.findByIdAndDayAndSlotGreaterThan((UUID) any(), (String) any(), (Integer) any()))
                .thenReturn(Optional.of(availability));
        ReservationController reservationController = new ReservationController(
                new ReservationService(reservationRepository, availabilityRepository));
        ReservationRequest reservationRequest = mock(ReservationRequest.class);
        when(reservationRequest.getEmail()).thenReturn("edwardakorlie73@gmail.com");
        when(reservationRequest.getTitle()).thenReturn("Quality assurance on Ai");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 1, 1).atStartOfDay();
        when(reservationRequest.getDate()).thenReturn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(reservationRequest.getAvailabilityId()).thenReturn(UUID.randomUUID());
        ResponseEntity<ReservationDto> actualCreateReservationResult = reservationController
                .createReservation(reservationRequest);
        assertTrue(actualCreateReservationResult.getHeaders().isEmpty());
        assertTrue(actualCreateReservationResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualCreateReservationResult.getStatusCode());
        ReservationDto body = actualCreateReservationResult.getBody();
        assert body != null;
        assertEquals("01:01", body.getEndTime().toString());
        assertEquals("edwardakorlie73@gmail.com", body.getEmail());
        assertSame(fromResult, body.getDate());
        assertEquals("Quality assurance on Ai", body.getTitle());
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
                UUID.randomUUID(), "edwardakorlie73@gmail.com");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.reservationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

