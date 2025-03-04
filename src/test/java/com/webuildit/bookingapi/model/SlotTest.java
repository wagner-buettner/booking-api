package com.webuildit.bookingapi.model;

import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    @Test
    void shouldCreateSlotWithValidDuration() {
        ZonedDateTime start = ZonedDateTime.of(2025, 3, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime end = start.plusHours(1);

        Slot slot = new Slot(start, end, new SalesManager()); // SalesManager cannot be null

        assertEquals(start, slot.getStartDate());
        assertEquals(end, slot.getEndDate());
    }

    @Test
    void shouldFailToCreateSlotWithInvalidDuration() {
        ZonedDateTime start = ZonedDateTime.of(2025, 3, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime end = start.plusMinutes(90); // Invalid duration (1h30)

        assertThrows(IllegalArgumentException.class, () -> {
            new Slot(start, end, new SalesManager());
        });
    }
}
