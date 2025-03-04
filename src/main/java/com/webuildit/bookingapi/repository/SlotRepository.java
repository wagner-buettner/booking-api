package com.webuildit.bookingapi.repository;

import com.webuildit.bookingapi.model.Slot;
import com.webuildit.bookingapi.projection.SlotProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query(value = """
    SELECT s.id AS id, s.start_date AS startDate, s.end_date AS endDate,
           s.booked AS booked, s.sales_manager_id AS salesManagerId
      FROM slots s
      JOIN sales_managers sm ON s.sales_manager_id = sm.id
     WHERE s.start_date BETWEEN :start AND :end
       AND s.booked = false
       AND NOT EXISTS (
            SELECT 1 FROM slots s2
             WHERE s2.sales_manager_id = s.sales_manager_id
               AND s2.start_date < s.end_date
               AND s2.end_date > s.start_date
               AND s2.booked = true
       )
       AND (:language IS NULL OR sm.languages @> ARRAY[CAST(:language AS VARCHAR)])
       AND (:products IS NULL OR sm.products && CAST(:products AS VARCHAR[]))
       AND (:rating IS NULL OR sm.customer_ratings @> ARRAY[CAST(:rating AS VARCHAR)])
    ORDER BY s.start_date
    """, nativeQuery = true)
    List<SlotProjection> findAvailableSlots(
        @Param("start") ZonedDateTime start,
        @Param("end") ZonedDateTime end,
        @Param("products") String[] products,
        @Param("language") String language,
        @Param("rating") String rating
    );

}
