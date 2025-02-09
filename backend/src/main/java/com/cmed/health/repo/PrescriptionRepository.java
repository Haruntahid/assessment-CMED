package com.cmed.health.repo;

import com.cmed.health.model.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;


@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {

    @Query(value = "SELECT DATE_FORMAT(p.prescription_date, '%d-%m-%Y') AS formatted_date, COUNT(*) FROM prescription p GROUP BY formatted_date", nativeQuery = true)
    List<Object[]> getDayWisePrescriptionCount();


    @Query(value = "SELECT * FROM prescription WHERE prescription_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Page<Prescription> findByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

}
