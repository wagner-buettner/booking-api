package com.webuildit.bookingapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record BookingRequest(
    @NotNull(message = "Date cannot be null") LocalDate date,
    @NotEmpty(message = "Products cannot be empty") List<String> products,
    @NotNull(message = "Language cannot be null") String language,
    @NotNull(message = "Rating cannot be null") String rating
) {}
