package com.giskard.ReservationProject.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.giskard.ReservationProject.converter.AvailabilityRequestToAvailabilityConverter;
import com.giskard.ReservationProject.converter.AvailabilityToAvailabilityDtoConverter;
import com.giskard.ReservationProject.exception.AvailabilityNotFoundException;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.repository.AvailabilityRepository;
import com.giskard.ReservationProject.request.AvailabilityRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AvailabilityService.class})
@ExtendWith(SpringExtension.class)
class AvailabilityServiceTest {
    @MockBean
    private AvailabilityRepository availabilityRepository;

    @MockBean
    private AvailabilityRequestToAvailabilityConverter availabilityRequestToAvailabilityConverter;

    @Autowired
    private AvailabilityService availabilityService;

    @MockBean
    private AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter;

    @Test
    void testCreateAvailabilities() {
        when(this.availabilityToAvailabilityDtoConverter.convert((Availability) any())).thenReturn(null);

        Availability availability = new Availability();
        availability.setDay("MONDAY");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        when(this.availabilityRequestToAvailabilityConverter.convert((AvailabilityRequest) any())).thenReturn(availability);

        Availability availability1 = new Availability();
        availability1.setDay("MONDAY");
        availability1.setEndTime(LocalTime.of(1, 1));
        availability1.setId(UUID.randomUUID());
        availability1.setSlot(1);
        availability1.setStartTime(LocalTime.of(1, 1));
        when(this.availabilityRepository.save((Availability) any())).thenReturn(availability1);
        assertNull(this.availabilityService.createAvailabilities(mock(AvailabilityRequest.class)));
        verify(this.availabilityToAvailabilityDtoConverter).convert((Availability) any());
        verify(this.availabilityRequestToAvailabilityConverter).convert((AvailabilityRequest) any());
        verify(this.availabilityRepository).save((Availability) any());
    }

    @Test
    void testCreateAvailabilities2() {
        when(this.availabilityToAvailabilityDtoConverter.convert((Availability) any())).thenReturn(null);

        Availability availability = new Availability();
        availability.setDay("TUESDAY");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        when(this.availabilityRequestToAvailabilityConverter.convert((AvailabilityRequest) any())).thenReturn(availability);
        when(this.availabilityRepository.save((Availability) any()))
                .thenThrow(new AvailabilityNotFoundException("An error occurred"));
        assertThrows(AvailabilityNotFoundException.class,
                () -> this.availabilityService.createAvailabilities(mock(AvailabilityRequest.class)));
        verify(this.availabilityRequestToAvailabilityConverter).convert((AvailabilityRequest) any());
        verify(this.availabilityRepository).save((Availability) any());
    }

    @Test
    void testCreateAvailabilities3() {
        when(this.availabilityToAvailabilityDtoConverter.convert((Availability) any())).thenReturn(null);
        when(this.availabilityRequestToAvailabilityConverter.convert((AvailabilityRequest) any()))
                .thenThrow(new AvailabilityNotFoundException("An error occurred"));
        assertThrows(AvailabilityNotFoundException.class,
                () -> this.availabilityService.createAvailabilities(mock(AvailabilityRequest.class)));
        verify(this.availabilityRequestToAvailabilityConverter).convert((AvailabilityRequest) any());
    }

    @Test
    void testDeleteAvailabilities() {
        Availability availability = new Availability();
        availability.setDay("WEDNESDAY");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        Optional<Availability> ofResult = Optional.of(availability);
        doNothing().when(this.availabilityRepository).delete((Availability) any());
        when(this.availabilityRepository.findById((UUID) any())).thenReturn(ofResult);
        this.availabilityService.deleteAvailabilities(UUID.randomUUID());
        verify(this.availabilityRepository).findById((UUID) any());
        verify(this.availabilityRepository).delete((Availability) any());
    }

    @Test
    void testDeleteAvailabilities2() {
        Availability availability = new Availability();
        availability.setDay("THURSDAY");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        Optional<Availability> ofResult = Optional.of(availability);
        doThrow(new AvailabilityNotFoundException("An error occurred")).when(this.availabilityRepository)
                .delete((Availability) any());
        when(this.availabilityRepository.findById((UUID) any())).thenReturn(ofResult);
        assertThrows(AvailabilityNotFoundException.class,
                () -> this.availabilityService.deleteAvailabilities(UUID.randomUUID()));
        verify(this.availabilityRepository).findById((UUID) any());
        verify(this.availabilityRepository).delete((Availability) any());
    }

    @Test
    void testDeleteAvailabilities3() {
        doNothing().when(this.availabilityRepository).delete((Availability) any());
        when(this.availabilityRepository.findById((UUID) any())).thenReturn(Optional.empty());
        assertThrows(AvailabilityNotFoundException.class,
                () -> this.availabilityService.deleteAvailabilities(UUID.randomUUID()));
        verify(this.availabilityRepository).findById((UUID) any());
    }

    @Test
    void testGetAvailabilities() {
        when(this.availabilityRepository.findByDayAndSlotGreaterThan((String) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertTrue(
                this.availabilityService.getAvailabilities(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()))
                        .isEmpty());
        verify(this.availabilityRepository).findByDayAndSlotGreaterThan((String) any(), (Integer) any());
    }

    @Test
    void testGetAvailabilities2() {
        when(this.availabilityRepository.findAll()).thenReturn(new ArrayList<>());
        when(this.availabilityRepository.findByDayAndSlotGreaterThan((String) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(this.availabilityService.getAvailabilities(null).isEmpty());
        verify(this.availabilityRepository).findAll();
    }

    @Test
    void testGetAvailabilities3() {
        when(this.availabilityRepository.findAll()).thenThrow(new AvailabilityNotFoundException("An error occurred"));
        when(this.availabilityRepository.findByDayAndSlotGreaterThan((String) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        assertThrows(AvailabilityNotFoundException.class, () -> this.availabilityService.getAvailabilities(null));
        verify(this.availabilityRepository).findAll();
    }
}

