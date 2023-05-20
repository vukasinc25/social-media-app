package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Report;
import com.ftn.kvtsvtprojekat.model.Report;
import com.ftn.kvtsvtprojekat.repository.ReportRepository;
import com.ftn.kvtsvtprojekat.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    public final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAll(){
        return reportRepository.findAll();
    }

    @Override
    public Report findOneById(Long id){
        return reportRepository.findReportById(id);
    }

    @Override
    public Report save(Report report){
        return reportRepository.save(report);
    }

    @Override
    public void delete(Long id){
        Report report = reportRepository.findReportById(id);
        report.setIsDeleted(true);
        reportRepository.save(report);
    }
}
