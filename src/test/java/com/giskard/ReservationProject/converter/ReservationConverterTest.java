package com.giskard.ReservationProject.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.giskard.ReservationProject.dto.ReservationDto;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.model.Reservation;
import com.giskard.ReservationProject.request.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class ReservationConverterTest {
    @Test
    void testConvertToEntity() {
        ReservationRequest reservationRequest = mock(ReservationRequest.class);
        when(reservationRequest.getEmail()).thenReturn("jane.doe@example.org");
        when(reservationRequest.getTitle()).thenReturn("A talk on Ai and ML");
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        when(reservationRequest.getDate()).thenReturn(fromResult);

        Availability availability = new Availability();
        availability.setDay("MONDAY");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        Reservation actualConvertToEntityResult = ReservationConverter.convertToEntity(reservationRequest, availability);
        assertSame(fromResult, actualConvertToEntityResult.getDate());
        assertEquals("A talk on Ai and ML", actualConvertToEntityResult.getTitle());
        assertEquals("01:01", actualConvertToEntityResult.getStartTime().toString());
        assertNull(actualConvertToEntityResult.getId());
        assertEquals("01:01", actualConvertToEntityResult.getEndTime().toString());
        assertEquals("jane.doe@example.org", actualConvertToEntityResult.getEmail());
        verify(reservationRequest).getEmail();
        verify(reservationRequest).getTitle();
        verify(reservationRequest).getDate();
    }

    @Test
    void testConvertToDto() {
        Reservation reservation = new Reservation();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        reservation.setDate(fromResult);
        reservation.setEmail("edwardakorlie73@gmail.com");
        reservation.setEndTime(LocalTime.of(1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        reservation.setId(randomUUIDResult);
        reservation.setStartTime(LocalTime.of(1, 1));
        reservation.setTitle("I have to join the innovation team Giskard let have a short meeting about that!");
        ReservationDto actualConvertToDtoResult = ReservationConverter.convertToDto(reservation);
        assertSame(fromResult, actualConvertToDtoResult.getDate());
        assertEquals("I have to join the innovation team Giskard let have a short meeting about that!", actualConvertToDtoResult.getTitle());
        assertEquals("01:01", actualConvertToDtoResult.getStartTime().toString());
        assertSame(randomUUIDResult, actualConvertToDtoResult.getId());
        assertEquals("01:01", actualConvertToDtoResult.getEndTime().toString());
        assertEquals("edwardakorlie73@gmail.com", actualConvertToDtoResult.getEmail());
    }
}

