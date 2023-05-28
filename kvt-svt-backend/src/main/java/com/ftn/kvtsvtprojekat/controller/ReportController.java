package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Report;
import com.ftn.kvtsvtprojekat.model.dto.ReportDTO;
import com.ftn.kvtsvtprojekat.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;
    private final ModelMapper modelMapper;

    public ReportController(ReportService reportService, ModelMapper modelMapper) {
        this.reportService = reportService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReportDTO>> getReports() {

        List<Report> reports = reportService.findAll();
        List<ReportDTO> reportsDTO = new ArrayList<>();
        for (Report report : reports) {

                ReportDTO reportDTO = modelMapper.map(report, ReportDTO.class);
                reportsDTO.add(reportDTO);
            }
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<ReportDTO>> getReportsDeleted() {

        List<Report> reports = reportService.findAll();
        List<ReportDTO> reportsDTO = new ArrayList<>();
        for (Report report : reports) {
//            if (report.getIsDeleted()) {
                ReportDTO reportDTO = modelMapper.map(report, ReportDTO.class);
                reportsDTO.add(reportDTO);
//            }
        }
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable("id") Long id) {
        Report report = reportService.findOneById(id);

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ReportDTO reportDTO = modelMapper.map(report, ReportDTO.class);

        return status(HttpStatus.OK).body(reportDTO);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Report> createReport(@Valid @RequestBody ReportDTO reportDTO) {
        Report report = modelMapper.map(reportDTO, Report.class);
        report.setReportTime(LocalDateTime.now());
//        report.setIsDeleted(false);
        report.setIsAccepted(false);
        reportService.save(report);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable("id") Long id) {
        Report group = reportService.findOneById(id);
        if(group == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
//        group.setApproved(true);

        reportService.save(group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Report report = reportService.findOneById(id);

        if (report != null) {
//            report.setIsDeleted(true);
            reportService.delete(report.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
