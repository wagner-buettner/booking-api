package com.webuildit.bookingapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.ZonedDateTime;

@Entity(name = "slots")
@Getter
@NoArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private boolean booked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_manager_id", nullable = false)
    private SalesManager salesManager;

    public Slot(ZonedDateTime startDate, ZonedDateTime endDate, SalesManager salesManager) {

        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        if (Duration.between(startDate, endDate).toMinutes() != 60) {
            throw new IllegalArgumentException("Slot duration must be exactly 1 hour.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.salesManager = salesManager;
        this.booked = false;
    }
}
