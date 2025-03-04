package com.webuildit.bookingapi.service;

import com.webuildit.bookingapi.dto.AvailableSlotResponse;
import com.webuildit.bookingapi.projection.SlotProjection;
import com.webuildit.bookingapi.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final SlotRepository slotRepository;

    public List<AvailableSlotResponse> getAvailableSlots(LocalDate date, List<String> products, String language, String rating) {
        var startOfDay = date.atStartOfDay(ZoneOffset.UTC);
        var endOfDay = date.plusDays(1).atStartOfDay(ZoneOffset.UTC);

        String[] productsArray = products.isEmpty() ? new String[]{} : products.toArray(new String[0]);

        List<SlotProjection> slots = slotRepository.findAvailableSlots(startOfDay, endOfDay, productsArray, language, rating);

        log.info("Found {} available slots for {}", slots.size(), date);

        return slots.stream()
            .collect(Collectors.groupingBy(
                slot -> slot.getStartDate().atZone(ZoneOffset.UTC),
                Collectors.summingInt(s -> 1)
            ))
            .entrySet().stream()
            .map(entry -> new AvailableSlotResponse(entry.getValue(), entry.getKey()))
            .toList();
    }

}
