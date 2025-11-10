package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
            + "FROM Sale AS obj "
            + "WHERE obj.date BETWEEN :min AND :max "
            + "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<SaleReportDTO> searchReport(LocalDate min, LocalDate max, String name, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "
            + "FROM Sale AS obj "
            + "WHERE obj.date BETWEEN :min AND :max "
            + "GROUP BY obj.seller.name")
    List<SaleSummaryDTO> searchSummary(LocalDate min, LocalDate max);
}
