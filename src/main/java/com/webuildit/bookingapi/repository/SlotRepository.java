package com.webuildit.bookingapi.repository;

import com.webuildit.bookingapi.model.Slot;
import com.webuildit.bookingapi.projection.AvailableSlotProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query(value = """
    SELECT COUNT(filtered_slots.id)::int AS available_count,
           filtered_slots.start_date
     FROM (
          SELECT s.id, s.start_date
            FROM slots s
            JOIN sales_managers sm ON s.sales_manager_id = sm.id
           WHERE DATE_TRUNC('DAY', s.start_date) = :date
             AND s.booked = FALSE
             AND NOT EXISTS (
                 SELECT 1
                   FROM slots s2
                  WHERE s2.sales_manager_id = s.sales_manager_id
                    AND s2.booked = TRUE
                    AND s2.start_date < s.end_date
                    AND s2.end_date > s.start_date
             )
             AND (:products IS NULL OR sm.products @> CAST(:products AS VARCHAR[]))
             AND (:language IS NULL OR sm.languages @> ARRAY[CAST(:language AS VARCHAR)])
             AND (:rating IS NULL OR sm.customer_ratings @> ARRAY[CAST(:rating AS VARCHAR)])
      ) filtered_slots
    GROUP BY filtered_slots.start_date
    ORDER BY filtered_slots.start_date;
    """, nativeQuery = true)
    List<AvailableSlotProjection> findAvailableSlots(
        @Param("date") LocalDate date,
        @Param("products") String[] products,
        @Param("language") String language,
        @Param("rating") String rating
    );

}
