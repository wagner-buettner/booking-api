package com.webuildit.bookingapi.service;

import com.webuildit.bookingapi.projection.SlotProjection;
import com.webuildit.bookingapi.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private BookingService bookingService;

    private List<SlotProjection> mockSlots;

    @BeforeEach
    void setUp() {
        mockSlots = List.of(
            createSlotProjection(1L, "2024-05-03T10:30:00Z", "2024-05-03T11:30:00Z", false, 100L),
            createSlotProjection(2L, "2024-05-03T11:00:00Z", "2024-05-03T12:00:00Z", false, 101L),
            createSlotProjection(3L, "2024-05-03T11:30:00Z", "2024-05-03T12:30:00Z", false, 100L)
        );
    }

    private SlotProjection createSlotProjection(Long id, String start, String end, Boolean booked, Long managerId) {
        return new SlotProjection() {
            @Override public Long getId() { return id; }
            @Override public Instant getStartDate() { return Instant.parse(start); }
            @Override public Instant getEndDate() { return Instant.parse(end); }
            @Override public Boolean getBooked() { return booked; }
            @Override public Long getSalesManagerId() { return managerId; }
        };
    }

    @Test
    void shouldReturnAvailableSlotsForMatchingCriteria() {
        LocalDate date = LocalDate.of(2024, 5, 3);
        List<String> products = List.of("SolarPanels", "Heatpumps");
        String language = "German";
        String rating = "Gold";

        when(slotRepository.findAvailableSlots(
            date.atStartOfDay(ZoneOffset.UTC),
            date.plusDays(1).atStartOfDay(ZoneOffset.UTC),
            products.toArray(new String[0]),
            language,
            rating
        )).thenReturn(mockSlots);

        var result = bookingService.getAvailableSlots(date, products, language, rating);

        assertThat(result).hasSize(3);
        assertThat(result.getFirst().startDate().getYear()).isEqualTo(2024);
        assertThat(result.getFirst().startDate().getMonthValue()).isEqualTo(5);
        assertThat(result.getFirst().startDate().getDayOfMonth()).isEqualTo(3);
    }

    @Test
    void shouldReturnSlotsWhenNoProductsAreProvided() {
        LocalDate date = LocalDate.of(2024, 5, 3);
        String language = "German";
        String rating = "Gold";

        when(slotRepository.findAvailableSlots(
            date.atStartOfDay(ZoneOffset.UTC),
            date.plusDays(1).atStartOfDay(ZoneOffset.UTC),
            new String[]{},
            language,
            rating
        )).thenReturn(mockSlots);

        var result = bookingService.getAvailableSlots(date, List.of(), language, rating);

        assertThat(result).hasSize(3);
    }


    @Test
    void shouldReturnSlotsWhenNoLanguageIsProvided() {
        LocalDate date = LocalDate.of(2024, 5, 3);
        List<String> products = List.of("SolarPanels", "Heatpumps");
        String rating = "Gold";

        when(slotRepository.findAvailableSlots(
            date.atStartOfDay(ZoneOffset.UTC),
            date.plusDays(1).atStartOfDay(ZoneOffset.UTC),
            products.toArray(new String[0]),
            null, // no given language
            rating
        )).thenReturn(mockSlots);

        var result = bookingService.getAvailableSlots(date, products, null, rating);

        assertThat(result).hasSize(3);
    }

    @Test
    void shouldReturnSlotsWhenNoRatingIsProvided() {
        LocalDate date = LocalDate.of(2024, 5, 3);
        List<String> products = List.of("SolarPanels", "Heatpumps");
        String language = "German";

        when(slotRepository.findAvailableSlots(
            date.atStartOfDay(ZoneOffset.UTC),
            date.plusDays(1).atStartOfDay(ZoneOffset.UTC),
            products.toArray(new String[0]),
            language,
            null // no given rating
        )).thenReturn(mockSlots);

        var result = bookingService.getAvailableSlots(date, products, language, null);

        assertThat(result).hasSize(3);
    }

    @Test
    void shouldReturnSlotsWhenNoCriteriaIsProvided() {
        LocalDate date = LocalDate.of(2024, 5, 3);

        when(slotRepository.findAvailableSlots(
            date.atStartOfDay(ZoneOffset.UTC),
            date.plusDays(1).atStartOfDay(ZoneOffset.UTC),
            new String[]{},
            null,
            null
        )).thenReturn(mockSlots);

        var result = bookingService.getAvailableSlots(date, List.of(), null, null);

        assertThat(result).hasSize(3);
    }

}
