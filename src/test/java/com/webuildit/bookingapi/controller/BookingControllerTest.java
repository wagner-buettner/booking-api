package com.webuildit.bookingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.webuildit.bookingapi.dto.AvailableSlotResponse;
import com.webuildit.bookingapi.dto.BookingRequest;
import com.webuildit.bookingapi.exception.GlobalExceptionHandler;
import com.webuildit.bookingapi.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(bookingController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldReturnAvailableSlotsWhenValidRequest() throws Exception {
        // Given
        BookingRequest request = new BookingRequest(
            LocalDate.of(2024, 5, 3),
            List.of("SolarPanels", "Heatpumps"),
            "German",
            "Gold"
        );

        List<AvailableSlotResponse> mockResponse = List.of(
            new AvailableSlotResponse(1, "2024-05-03T10:30:00.000Z"),
            new AvailableSlotResponse(2, "2024-05-03T11:00:00.000Z")
        );

        when(bookingService.getAvailableSlots(request.date(), request.products(), request.language(), request.rating()))
            .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/calendar/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].available_count").value(1))
            .andExpect(jsonPath("$[0].start_date").value("2024-05-03T10:30:00.000Z"))
            .andExpect(jsonPath("$[1].available_count").value(2))
            .andExpect(jsonPath("$[1].start_date").value("2024-05-03T11:00:00.000Z"));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidRequest() throws Exception {
        BookingRequest invalidRequest = new BookingRequest(null, List.of(), "German", "Gold");

        mockMvc.perform(post("/calendar/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[*]").value(containsInAnyOrder(
                "date: Date cannot be null",
                "products: Products cannot be empty"
            )));
    }

}
