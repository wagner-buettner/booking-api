package com.webuildit.bookingapi.service;

import com.webuildit.bookingapi.dto.AvailableSlotResponse;
import com.webuildit.bookingapi.projection.AvailableSlotProjection;
import com.webuildit.bookingapi.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final SlotRepository slotRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public List<AvailableSlotResponse> getAvailableSlots(LocalDate date, List<String> products, String language, String rating) {
        String[] productsArray = (products == null || products.isEmpty()) ? null : products.toArray(new String[0]);

        List<AvailableSlotProjection> slots = slotRepository.findAvailableSlots(date, productsArray, language, rating);

        log.info("🔍 Found {} available slots for {}", slots.size(), date);

        return slots.stream()
            .map(slot -> {
                ZonedDateTime utcTime = slot.getStartDate().atZone(ZoneOffset.UTC);
                return new AvailableSlotResponse(slot.getAvailableCount(), utcTime.format(FORMATTER));
            })
            .toList();
    }
}
