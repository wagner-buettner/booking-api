package com.webuildit.bookingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.webuildit.bookingapi.service.BookingService;
import com.webuildit.bookingapi.dto.BookingRequest;
import com.webuildit.bookingapi.dto.AvailableSlotResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void shouldReturnAvailableSlots() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        BookingRequest request = new BookingRequest(LocalDate.of(2024, 5, 3), List.of("SolarPanels"), "German", "Bronze");

        AvailableSlotResponse response = AvailableSlotResponse.builder()
            .startDate(request.date().atStartOfDay(ZoneOffset.UTC))
            .availableCount(2)
            .build();

        when(bookingService.getAvailableSlots(request.date(), request.products(), request.language(), request.rating()))
            .thenReturn(List.of(response));

        mockMvc.perform(post("/calendar/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].availableCount").value(2));
    }

}
