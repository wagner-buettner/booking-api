package com.webuildit.bookingapi.repository;

import com.webuildit.bookingapi.model.SalesManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesManagerRepository extends JpaRepository<SalesManager, Long> {
}
