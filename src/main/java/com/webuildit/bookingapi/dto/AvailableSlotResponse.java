package com.webuildit.bookingapi.dto;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record AvailableSlotResponse(int availableCount, ZonedDateTime startDate) {
}
