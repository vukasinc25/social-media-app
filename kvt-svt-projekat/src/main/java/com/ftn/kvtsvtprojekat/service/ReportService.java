package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Report;

import java.util.List;

public interface ReportService {
    List<Report> findAll();

    Report findOneById(Long id);

    Report save(Report report);

    void delete(Long id);
}
