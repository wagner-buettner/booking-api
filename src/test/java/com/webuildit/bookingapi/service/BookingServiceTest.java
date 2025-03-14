package com.webuildit.bookingapi.service;

import com.webuildit.bookingapi.dto.AvailableSlotResponse;
import com.webuildit.bookingapi.projection.AvailableSlotProjection;
import com.webuildit.bookingapi.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private BookingService bookingService;

    private List<AvailableSlotProjection> mockProjections;

    @BeforeEach
    void setUp() {
        AvailableSlotProjection slot1 = mock(AvailableSlotProjection.class);
        AvailableSlotProjection slot2 = mock(AvailableSlotProjection.class);

        when(slot1.getAvailableCount()).thenReturn(1);
        when(slot1.getStartDate()).thenReturn(Instant.parse("2024-05-03T10:30:00Z"));

        when(slot2.getAvailableCount()).thenReturn(2);
        when(slot2.getStartDate()).thenReturn(Instant.parse("2024-05-03T11:00:00Z"));

        mockProjections = List.of(slot1, slot2);
    }

    @Test
    void shouldReturnCorrectAvailableSlots() {
        // Given
        LocalDate date = LocalDate.of(2024, 5, 3);
        List<String> products = List.of("SolarPanels", "Heatpumps");
        String language = "German";
        String rating = "Gold";

        when(slotRepository.findAvailableSlots(date, products.toArray(new String[0]), language, rating))
            .thenReturn(mockProjections);

        // When
        List<AvailableSlotResponse> result = bookingService.getAvailableSlots(date, products, language, rating);

        // Then
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).availableCount());
        assertEquals("2024-05-03T10:30:00.000Z", result.get(0).startDate());

        assertEquals(2, result.get(1).availableCount());
        assertEquals("2024-05-03T11:00:00.000Z", result.get(1).startDate());

        verify(slotRepository, times(1)).findAvailableSlots(date, products.toArray(new String[0]), language, rating);
    }
}
