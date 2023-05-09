package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Report;
import com.ftn.kvtsvtprojekat.model.Report;
import com.ftn.kvtsvtprojekat.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl {
    public final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> findAll(){
        return reportRepository.findAll();
    }

    public Report findOneById(Long id){
        return reportRepository.findReportById(id);
    }

    public Report addReport(Report report){
        return reportRepository.save(report);
    }

    public Report updateReport(Report report){
        return reportRepository.save(report);
    }

    public Report deleteReport(Long id){
        return reportRepository.deleteReportById(id);
    }
}
