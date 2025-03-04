package com.webuildit.bookingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record BookingRequest(@NotNull LocalDate date, @NotEmpty List<String> products, @NotBlank String language, @NotBlank String rating) {

}
