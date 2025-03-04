package com.webuildit.bookingapi.controller;

import com.webuildit.bookingapi.dto.AvailableSlotResponse;
import com.webuildit.bookingapi.dto.BookingRequest;
import com.webuildit.bookingapi.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Calendar", description = "API for checking available booking slots based on customer criteria.")
@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Retrieve available booking slots", description = "Fetch available booking slots based on customer criteria. " +
                                                                           "Note: Time slots may overlap, but a Sales Manager cannot be double-booked.")
    @PostMapping("/query")
    public ResponseEntity<List<AvailableSlotResponse>> getAvailableSlots(
        @Valid @RequestBody BookingRequest request) {
        List<AvailableSlotResponse> availableSlots = bookingService.getAvailableSlots(
            request.date(),
            request.products(),
            request.language(),
            request.rating()
        );
        return ResponseEntity.ok(availableSlots);
    }
}
