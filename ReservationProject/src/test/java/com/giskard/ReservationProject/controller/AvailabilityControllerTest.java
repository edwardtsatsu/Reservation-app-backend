package com.giskard.ReservationProject.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giskard.ReservationProject.service.AvailabilityService;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    void testGetAvailabilities() throws Exception {
        when(this.availabilityService.getAvailabilities()).thenReturn(new ArrayList<>());
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
        when(this.availabilityService.getAvailabilities()).thenReturn(new ArrayList<>());
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
    void testCreateAvailabilities() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/availabilities")
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString(null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testDeleteAvailabilities() throws Exception {
        doNothing().when(this.availabilityService).deleteAvailabilities((UUID) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/availabilities/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteAvailabilities2() throws Exception {
        doNothing().when(this.availabilityService).deleteAvailabilities((UUID) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/availabilities/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.availabilityController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

