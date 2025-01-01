package com.inventory.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gst-reports")
@RequiredArgsConstructor
public class GstReportController {
    private final GstReportService gstReportService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> generateReport(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false, defaultValue = "GSTR1") String reportType) {
        return ResponseEntity.ok(gstReportService.generateGstReport(startDate, endDate, reportType));
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadReport(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String reportType,
            @RequestParam(required = false, defaultValue = "EXCEL") String format) {
        byte[] reportContent = gstReportService.downloadReport(startDate, endDate, reportType, format);
        
        String filename = String.format("GST_%s_%s_%s.%s", 
            reportType, startDate, endDate, 
            format.equalsIgnoreCase("EXCEL") ? "xlsx" : "json");
            
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(format.equalsIgnoreCase("EXCEL") ? 
                MediaType.APPLICATION_OCTET_STREAM : MediaType.APPLICATION_JSON)
            .body(reportContent);
    }
} 