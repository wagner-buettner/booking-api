package com.webuildit.bookingapi.projection;

import java.time.Instant;

public interface SlotProjection {
    Long getId();
    Instant getStartDate();
    Instant getEndDate();
    Boolean getBooked();
    Long getSalesManagerId();
}
