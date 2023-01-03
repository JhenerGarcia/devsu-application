package com.devsu.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.configuration.ApiException;
import com.devsu.dto.ApiData;
import com.devsu.dto.ApiResult;
import com.devsu.dto.Report;
import com.devsu.service.ReportService;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/by-account")
    public ResponseEntity<ApiResult> reportByAccount(
            @RequestParam(name = "accountNumber", required = true) String accountNumber,
            @RequestParam(name = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(name = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo)
            throws ApiException {

        Report report = reportService.generateReport(accountNumber, dateFrom, dateTo);

        return ResponseEntity.ok(new ApiResult(String.valueOf(HttpStatus.OK.value()), "Success", LocalDateTime.now(),
                new ApiData("Report", report)));
    }

}
