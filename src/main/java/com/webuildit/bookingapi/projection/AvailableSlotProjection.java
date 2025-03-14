package com.webuildit.bookingapi.projection;

import java.time.Instant;

public interface AvailableSlotProjection {
    int getAvailableCount();
    Instant getStartDate();
}
