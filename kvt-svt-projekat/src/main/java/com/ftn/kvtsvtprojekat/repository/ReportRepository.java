package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
    Report findReportById(Long id);

    Report deleteReportById(Long id);
}
