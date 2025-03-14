package com.webuildit.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AvailableSlotResponse(
    @JsonProperty("available_count") int availableCount,
    @JsonProperty("start_date") String startDate
) {}
