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
        availability.setDay("MONDAY");
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
        assertEquals("MONDAY", body.getDay());
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
    void testDeleteAvailabilities() throws Exception {
        doNothing().when(this.availabilityService).deleteAvailabilities((UUID) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/availabilities/{id}",
                UUID.randomUUID());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}

