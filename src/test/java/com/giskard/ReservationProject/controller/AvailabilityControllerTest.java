package com.giskard.ReservationProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.giskard.ReservationProject.constant.Day;
import com.giskard.ReservationProject.converter.AvailabilityRequestToAvailabilityConverter;
import com.giskard.ReservationProject.converter.AvailabilityToAvailabilityDtoConverter;
import com.giskard.ReservationProject.dto.AvailabilityDto;
import com.giskard.ReservationProject.model.Availability;
import com.giskard.ReservationProject.repository.AvailabilityRepository;
import com.giskard.ReservationProject.request.AvailabilityRequest;
import com.giskard.ReservationProject.service.AvailabilityService;

import java.time.LocalTime;
import java.util.ArrayList;
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

@ContextConfiguration(classes = {AvailabilityController.class})
@ExtendWith(SpringExtension.class)
class AvailabilityControllerTest {
    @Autowired
    private AvailabilityController availabilityController;

    @MockBean
    private AvailabilityService availabilityService;

    @Test
    void testCreateAvailabilities() {
        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        availability.setId(randomUUIDResult);
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.save((Availability) any())).thenReturn(availability);
        AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter = new AvailabilityToAvailabilityDtoConverter();
        AvailabilityController availabilityController = new AvailabilityController(
                new AvailabilityService(availabilityRepository, availabilityToAvailabilityDtoConverter,
                        new AvailabilityRequestToAvailabilityConverter()));
        AvailabilityRequest availabilityRequest = mock(AvailabilityRequest.class);
        when(availabilityRequest.getSlot()).thenReturn(1);
        when(availabilityRequest.getDay()).thenReturn(Day.MONDAY);
        when(availabilityRequest.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availabilityRequest.getStartTime()).thenReturn(LocalTime.of(1, 1));
        ResponseEntity<AvailabilityDto> actualCreateAvailabilitiesResult = availabilityController
                .createAvailabilities(availabilityRequest);
        assertTrue(actualCreateAvailabilitiesResult.getHeaders().isEmpty());
        assertTrue(actualCreateAvailabilitiesResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualCreateAvailabilitiesResult.getStatusCode());
        AvailabilityDto body = actualCreateAvailabilitiesResult.getBody();
        assert body != null;
        assertEquals("01:01", body.getEndTime());
        assertEquals("Day", body.getDay());
        assertEquals("01:01", body.getStartTime());
        assertSame(randomUUIDResult, body.getId());
        assertEquals(1, body.getSlot());
        verify(availabilityRepository).save(any());
        verify(availabilityRequest).getDay();
        verify(availabilityRequest).getSlot();
        verify(availabilityRequest).getEndTime();
        verify(availabilityRequest).getStartTime();
    }

    @Test
    void testCreateAvailabilities2() {
        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));

        Availability availability1 = new Availability();
        availability1.setDay("Day");
        availability1.setEndTime(LocalTime.of(1, 1));
        availability1.setId(UUID.randomUUID());
        availability1.setSlot(1);
        availability1.setStartTime(LocalTime.of(1, 1));

        Availability availability2 = new Availability();
        availability2.setDay("Day");
        availability2.setEndTime(LocalTime.of(1, 1));
        availability2.setId(UUID.randomUUID());
        availability2.setSlot(1);
        availability2.setStartTime(LocalTime.of(1, 1));

        Availability availability3 = new Availability();
        availability3.setDay("Day");
        availability3.setEndTime(LocalTime.of(1, 1));
        availability3.setId(UUID.randomUUID());
        availability3.setSlot(1);
        availability3.setStartTime(LocalTime.of(1, 1));

        Availability availability4 = new Availability();
        availability4.setDay("Day");
        availability4.setEndTime(LocalTime.of(1, 1));
        availability4.setId(UUID.randomUUID());
        availability4.setSlot(1);
        availability4.setStartTime(LocalTime.of(1, 1));
        Availability availability5 = mock(Availability.class);
        when(availability5.getSlot()).thenReturn(1);
        when(availability5.getDay()).thenReturn("Day");
        when(availability5.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availability5.getId()).thenReturn(UUID.randomUUID());
        when(availability5.setDay((String) any())).thenReturn(availability);
        when(availability5.setEndTime((LocalTime) any())).thenReturn(availability1);
        when(availability5.setId((UUID) any())).thenReturn(availability2);
        when(availability5.setSlot(anyInt())).thenReturn(availability3);
        when(availability5.setStartTime((LocalTime) any())).thenReturn(availability4);
        when(availability5.getStartTime()).thenReturn(LocalTime.of(1, 1));
        availability5.setDay("Day");
        availability5.setEndTime(LocalTime.of(1, 1));
        availability5.setId(UUID.randomUUID());
        availability5.setSlot(1);
        availability5.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.save((Availability) any())).thenReturn(availability5);
        AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter = mock(
                AvailabilityToAvailabilityDtoConverter.class);
        when(availabilityToAvailabilityDtoConverter.convert((Availability) any())).thenReturn(null);
        AvailabilityController availabilityController = new AvailabilityController(
                new AvailabilityService(availabilityRepository, availabilityToAvailabilityDtoConverter,
                        new AvailabilityRequestToAvailabilityConverter()));
        AvailabilityRequest availabilityRequest = mock(AvailabilityRequest.class);
        when(availabilityRequest.getSlot()).thenReturn(1);
        when(availabilityRequest.getDay()).thenReturn(Day.MONDAY);
        when(availabilityRequest.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availabilityRequest.getStartTime()).thenReturn(LocalTime.of(1, 1));
        ResponseEntity<AvailabilityDto> actualCreateAvailabilitiesResult = availabilityController
                .createAvailabilities(availabilityRequest);
        assertNull(actualCreateAvailabilitiesResult.getBody());
        assertEquals("<201 CREATED Created,[]>", actualCreateAvailabilitiesResult.toString());
        assertEquals(HttpStatus.CREATED, actualCreateAvailabilitiesResult.getStatusCode());
        assertTrue(actualCreateAvailabilitiesResult.getHeaders().isEmpty());
        verify(availabilityRepository).save((Availability) any());
        verify(availability5).setDay((String) any());
        verify(availability5).setEndTime((LocalTime) any());
        verify(availability5).setId((UUID) any());
        verify(availability5).setSlot(anyInt());
        verify(availability5).setStartTime((LocalTime) any());
        verify(availabilityToAvailabilityDtoConverter).convert((Availability) any());
        verify(availabilityRequest).getDay();
        verify(availabilityRequest).getSlot();
        verify(availabilityRequest).getEndTime();
        verify(availabilityRequest).getStartTime();
    }

    @Test
    void testCreateAvailabilities3() {
        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));

        Availability availability1 = new Availability();
        availability1.setDay("Day");
        availability1.setEndTime(LocalTime.of(1, 1));
        availability1.setId(UUID.randomUUID());
        availability1.setSlot(1);
        availability1.setStartTime(LocalTime.of(1, 1));

        Availability availability2 = new Availability();
        availability2.setDay("Day");
        availability2.setEndTime(LocalTime.of(1, 1));
        availability2.setId(UUID.randomUUID());
        availability2.setSlot(1);
        availability2.setStartTime(LocalTime.of(1, 1));

        Availability availability3 = new Availability();
        availability3.setDay("Day");
        availability3.setEndTime(LocalTime.of(1, 1));
        availability3.setId(UUID.randomUUID());
        availability3.setSlot(1);
        availability3.setStartTime(LocalTime.of(1, 1));

        Availability availability4 = new Availability();
        availability4.setDay("Day");
        availability4.setEndTime(LocalTime.of(1, 1));
        availability4.setId(UUID.randomUUID());
        availability4.setSlot(1);
        availability4.setStartTime(LocalTime.of(1, 1));
        Availability availability5 = mock(Availability.class);
        when(availability5.getSlot()).thenReturn(1);
        when(availability5.getDay()).thenReturn("Day");
        when(availability5.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availability5.getId()).thenReturn(UUID.randomUUID());
        when(availability5.setDay((String) any())).thenReturn(availability);
        when(availability5.setEndTime((LocalTime) any())).thenReturn(availability1);
        when(availability5.setId((UUID) any())).thenReturn(availability2);
        when(availability5.setSlot(anyInt())).thenReturn(availability3);
        when(availability5.setStartTime((LocalTime) any())).thenReturn(availability4);
        when(availability5.getStartTime()).thenReturn(LocalTime.of(1, 1));
        availability5.setDay("Day");
        availability5.setEndTime(LocalTime.of(1, 1));
        availability5.setId(UUID.randomUUID());
        availability5.setSlot(1);
        availability5.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.save((Availability) any())).thenReturn(availability5);
        AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter = mock(
                AvailabilityToAvailabilityDtoConverter.class);
        when(availabilityToAvailabilityDtoConverter.convert((Availability) any())).thenReturn(null);

        Availability availability6 = new Availability();
        availability6.setDay("Day");
        availability6.setEndTime(LocalTime.of(1, 1));
        availability6.setId(UUID.randomUUID());
        availability6.setSlot(1);
        availability6.setStartTime(LocalTime.of(1, 1));
        AvailabilityRequestToAvailabilityConverter availabilityRequestToAvailabilityConverter = mock(
                AvailabilityRequestToAvailabilityConverter.class);
        when(availabilityRequestToAvailabilityConverter.convert((AvailabilityRequest) any())).thenReturn(availability6);
        AvailabilityController availabilityController = new AvailabilityController(new AvailabilityService(
                availabilityRepository, availabilityToAvailabilityDtoConverter, availabilityRequestToAvailabilityConverter));
        AvailabilityRequest availabilityRequest = mock(AvailabilityRequest.class);
        when(availabilityRequest.getSlot()).thenReturn(1);
        when(availabilityRequest.getDay()).thenReturn(Day.MONDAY);
        when(availabilityRequest.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availabilityRequest.getStartTime()).thenReturn(LocalTime.of(1, 1));
        ResponseEntity<AvailabilityDto> actualCreateAvailabilitiesResult = availabilityController
                .createAvailabilities(availabilityRequest);
        assertNull(actualCreateAvailabilitiesResult.getBody());
        assertEquals("<201 CREATED Created,[]>", actualCreateAvailabilitiesResult.toString());
        assertEquals(HttpStatus.CREATED, actualCreateAvailabilitiesResult.getStatusCode());
        assertTrue(actualCreateAvailabilitiesResult.getHeaders().isEmpty());
        verify(availabilityRepository).save((Availability) any());
        verify(availability5).setDay((String) any());
        verify(availability5).setEndTime((LocalTime) any());
        verify(availability5).setId((UUID) any());
        verify(availability5).setSlot(anyInt());
        verify(availability5).setStartTime((LocalTime) any());
        verify(availabilityToAvailabilityDtoConverter).convert((Availability) any());
        verify(availabilityRequestToAvailabilityConverter).convert((AvailabilityRequest) any());
    }

    @Test
    void testCreateAvailabilities4() {
        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        availability.setId(randomUUIDResult);
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.save((Availability) any())).thenReturn(availability);
        AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter = new AvailabilityToAvailabilityDtoConverter();
        AvailabilityController availabilityController = new AvailabilityController(
                new AvailabilityService(availabilityRepository, availabilityToAvailabilityDtoConverter,
                        new AvailabilityRequestToAvailabilityConverter()));
        AvailabilityRequest availabilityRequest = mock(AvailabilityRequest.class);
        when(availabilityRequest.getSlot()).thenReturn(1);
        when(availabilityRequest.getDay()).thenReturn(Day.MONDAY);
        when(availabilityRequest.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availabilityRequest.getStartTime()).thenReturn(LocalTime.of(1, 1));
        ResponseEntity<AvailabilityDto> actualCreateAvailabilitiesResult = availabilityController
                .createAvailabilities(availabilityRequest);
        assertTrue(actualCreateAvailabilitiesResult.getHeaders().isEmpty());
        assertTrue(actualCreateAvailabilitiesResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualCreateAvailabilitiesResult.getStatusCode());
        AvailabilityDto body = actualCreateAvailabilitiesResult.getBody();
        assertEquals("01:01", body.getEndTime());
        assertEquals("Day", body.getDay());
        assertEquals("01:01", body.getStartTime());
        assertSame(randomUUIDResult, body.getId());
        assertEquals(1, body.getSlot());
        verify(availabilityRepository).save((Availability) any());
        verify(availabilityRequest).getDay();
        verify(availabilityRequest).getSlot();
        verify(availabilityRequest).getEndTime();
        verify(availabilityRequest).getStartTime();
    }

    @Test
    void testCreateAvailabilities5() {
        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));

        Availability availability1 = new Availability();
        availability1.setDay("Day");
        availability1.setEndTime(LocalTime.of(1, 1));
        availability1.setId(UUID.randomUUID());
        availability1.setSlot(1);
        availability1.setStartTime(LocalTime.of(1, 1));

        Availability availability2 = new Availability();
        availability2.setDay("Day");
        availability2.setEndTime(LocalTime.of(1, 1));
        availability2.setId(UUID.randomUUID());
        availability2.setSlot(1);
        availability2.setStartTime(LocalTime.of(1, 1));

        Availability availability3 = new Availability();
        availability3.setDay("Day");
        availability3.setEndTime(LocalTime.of(1, 1));
        availability3.setId(UUID.randomUUID());
        availability3.setSlot(1);
        availability3.setStartTime(LocalTime.of(1, 1));

        Availability availability4 = new Availability();
        availability4.setDay("Day");
        availability4.setEndTime(LocalTime.of(1, 1));
        availability4.setId(UUID.randomUUID());
        availability4.setSlot(1);
        availability4.setStartTime(LocalTime.of(1, 1));
        Availability availability5 = mock(Availability.class);
        when(availability5.getSlot()).thenReturn(1);
        when(availability5.getDay()).thenReturn("Day");
        when(availability5.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availability5.getId()).thenReturn(UUID.randomUUID());
        when(availability5.setDay((String) any())).thenReturn(availability);
        when(availability5.setEndTime((LocalTime) any())).thenReturn(availability1);
        when(availability5.setId((UUID) any())).thenReturn(availability2);
        when(availability5.setSlot(anyInt())).thenReturn(availability3);
        when(availability5.setStartTime((LocalTime) any())).thenReturn(availability4);
        when(availability5.getStartTime()).thenReturn(LocalTime.of(1, 1));
        availability5.setDay("Day");
        availability5.setEndTime(LocalTime.of(1, 1));
        availability5.setId(UUID.randomUUID());
        availability5.setSlot(1);
        availability5.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.save((Availability) any())).thenReturn(availability5);
        AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter = mock(
                AvailabilityToAvailabilityDtoConverter.class);
        when(availabilityToAvailabilityDtoConverter.convert((Availability) any())).thenReturn(null);
        AvailabilityController availabilityController = new AvailabilityController(
                new AvailabilityService(availabilityRepository, availabilityToAvailabilityDtoConverter,
                        new AvailabilityRequestToAvailabilityConverter()));
        AvailabilityRequest availabilityRequest = mock(AvailabilityRequest.class);
        when(availabilityRequest.getSlot()).thenReturn(1);
        when(availabilityRequest.getDay()).thenReturn(Day.MONDAY);
        when(availabilityRequest.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availabilityRequest.getStartTime()).thenReturn(LocalTime.of(1, 1));
        ResponseEntity<AvailabilityDto> actualCreateAvailabilitiesResult = availabilityController
                .createAvailabilities(availabilityRequest);
        assertNull(actualCreateAvailabilitiesResult.getBody());
        assertEquals("<201 CREATED Created,[]>", actualCreateAvailabilitiesResult.toString());
        assertEquals(HttpStatus.CREATED, actualCreateAvailabilitiesResult.getStatusCode());
        assertTrue(actualCreateAvailabilitiesResult.getHeaders().isEmpty());
        verify(availabilityRepository).save((Availability) any());
        verify(availability5).setDay((String) any());
        verify(availability5).setEndTime((LocalTime) any());
        verify(availability5).setId((UUID) any());
        verify(availability5).setSlot(anyInt());
        verify(availability5).setStartTime((LocalTime) any());
        verify(availabilityToAvailabilityDtoConverter).convert((Availability) any());
        verify(availabilityRequest).getDay();
        verify(availabilityRequest).getSlot();
        verify(availabilityRequest).getEndTime();
        verify(availabilityRequest).getStartTime();
    }

    @Test
    void testCreateAvailabilities6() {
        Availability availability = new Availability();
        availability.setDay("Day");
        availability.setEndTime(LocalTime.of(1, 1));
        availability.setId(UUID.randomUUID());
        availability.setSlot(1);
        availability.setStartTime(LocalTime.of(1, 1));

        Availability availability1 = new Availability();
        availability1.setDay("Day");
        availability1.setEndTime(LocalTime.of(1, 1));
        availability1.setId(UUID.randomUUID());
        availability1.setSlot(1);
        availability1.setStartTime(LocalTime.of(1, 1));

        Availability availability2 = new Availability();
        availability2.setDay("Day");
        availability2.setEndTime(LocalTime.of(1, 1));
        availability2.setId(UUID.randomUUID());
        availability2.setSlot(1);
        availability2.setStartTime(LocalTime.of(1, 1));

        Availability availability3 = new Availability();
        availability3.setDay("Day");
        availability3.setEndTime(LocalTime.of(1, 1));
        availability3.setId(UUID.randomUUID());
        availability3.setSlot(1);
        availability3.setStartTime(LocalTime.of(1, 1));

        Availability availability4 = new Availability();
        availability4.setDay("Day");
        availability4.setEndTime(LocalTime.of(1, 1));
        availability4.setId(UUID.randomUUID());
        availability4.setSlot(1);
        availability4.setStartTime(LocalTime.of(1, 1));
        Availability availability5 = mock(Availability.class);
        when(availability5.getSlot()).thenReturn(1);
        when(availability5.getDay()).thenReturn("Day");
        when(availability5.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availability5.getId()).thenReturn(UUID.randomUUID());
        when(availability5.setDay((String) any())).thenReturn(availability);
        when(availability5.setEndTime((LocalTime) any())).thenReturn(availability1);
        when(availability5.setId((UUID) any())).thenReturn(availability2);
        when(availability5.setSlot(anyInt())).thenReturn(availability3);
        when(availability5.setStartTime((LocalTime) any())).thenReturn(availability4);
        when(availability5.getStartTime()).thenReturn(LocalTime.of(1, 1));
        availability5.setDay("Day");
        availability5.setEndTime(LocalTime.of(1, 1));
        availability5.setId(UUID.randomUUID());
        availability5.setSlot(1);
        availability5.setStartTime(LocalTime.of(1, 1));
        AvailabilityRepository availabilityRepository = mock(AvailabilityRepository.class);
        when(availabilityRepository.save((Availability) any())).thenReturn(availability5);
        AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter = mock(
                AvailabilityToAvailabilityDtoConverter.class);
        when(availabilityToAvailabilityDtoConverter.convert((Availability) any())).thenReturn(null);

        Availability availability6 = new Availability();
        availability6.setDay("Day");
        availability6.setEndTime(LocalTime.of(1, 1));
        availability6.setId(UUID.randomUUID());
        availability6.setSlot(1);
        availability6.setStartTime(LocalTime.of(1, 1));
        AvailabilityRequestToAvailabilityConverter availabilityRequestToAvailabilityConverter = mock(
                AvailabilityRequestToAvailabilityConverter.class);
        when(availabilityRequestToAvailabilityConverter.convert((AvailabilityRequest) any())).thenReturn(availability6);
        AvailabilityController availabilityController = new AvailabilityController(new AvailabilityService(
                availabilityRepository, availabilityToAvailabilityDtoConverter, availabilityRequestToAvailabilityConverter));
        AvailabilityRequest availabilityRequest = mock(AvailabilityRequest.class);
        when(availabilityRequest.getSlot()).thenReturn(1);
        when(availabilityRequest.getDay()).thenReturn(Day.MONDAY);
        when(availabilityRequest.getEndTime()).thenReturn(LocalTime.of(1, 1));
        when(availabilityRequest.getStartTime()).thenReturn(LocalTime.of(1, 1));
        ResponseEntity<AvailabilityDto> actualCreateAvailabilitiesResult = availabilityController
                .createAvailabilities(availabilityRequest);
        assertNull(actualCreateAvailabilitiesResult.getBody());
        assertEquals("<201 CREATED Created,[]>", actualCreateAvailabilitiesResult.toString());
        assertEquals(HttpStatus.CREATED, actualCreateAvailabilitiesResult.getStatusCode());
        assertTrue(actualCreateAvailabilitiesResult.getHeaders().isEmpty());
        verify(availabilityRepository).save((Availability) any());
        verify(availability5).setDay((String) any());
        verify(availability5).setEndTime((LocalTime) any());
        verify(availability5).setId((UUID) any());
        verify(availability5).setSlot(anyInt());
        verify(availability5).setStartTime((LocalTime) any());
        verify(availabilityToAvailabilityDtoConverter).convert((Availability) any());
        verify(availabilityRequestToAvailabilityConverter).convert((AvailabilityRequest) any());
    }

    @Test
    void testGetAvailabilities() throws Exception {
        when(this.availabilityService.getAvailabilities((java.util.Date) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/availabilities");
        MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAvailabilities2() throws Exception {
        when(this.availabilityService.getAvailabilities((java.util.Date) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/availabilities");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAvailabilities3() throws Exception {
        when(this.availabilityService.getAvailabilities((java.util.Date) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/availabilities");
        MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAvailabilities4() throws Exception {
        when(this.availabilityService.getAvailabilities((java.util.Date) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/availabilities");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testDeleteAvailabilities() throws Exception {
        doNothing().when(this.availabilityService).deleteAvailabilities((UUID) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/availabilities/{id}",
                UUID.randomUUID());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteAvailabilities2() throws Exception {
        doNothing().when(this.availabilityService).deleteAvailabilities((UUID) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/availabilities/{id}",
                UUID.randomUUID());
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteAvailabilities3() throws Exception {
        doNothing().when(this.availabilityService).deleteAvailabilities((UUID) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/availabilities/{id}",
                UUID.randomUUID());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteAvailabilities4() throws Exception {
        doNothing().when(this.availabilityService).deleteAvailabilities((UUID) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/availabilities/{id}",
                UUID.randomUUID());
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

