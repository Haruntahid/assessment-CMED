package com.cmed.health.repo;

import com.cmed.health.model.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    @Query(value = "SELECT CAST(p.prescription_date AS DATE) AS formatted_date, COUNT(*) " +
            "FROM prescription p " +
            "GROUP BY CAST(p.prescription_date AS DATE) " +
            "ORDER BY formatted_date ASC",
            nativeQuery = true)
    List<Object[]> getDayWisePrescriptionCount();

    @Query(value = "SELECT * FROM prescription WHERE prescription_date BETWEEN :startDate AND :endDate ORDER BY prescription_date ASC", nativeQuery = true)
    Page<Prescription> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

}
