package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Report;
import com.ftn.kvtsvtprojekat.repository.ReportRepository;
import com.ftn.kvtsvtprojekat.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    public final ReportRepository reportRepository;
    private final ModelMapper modelMapper;

    public ReportServiceImpl(ReportRepository reportRepository, ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
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
    public Report delete(Long id){
        return reportRepository.deleteReportById(id);
    }
}
