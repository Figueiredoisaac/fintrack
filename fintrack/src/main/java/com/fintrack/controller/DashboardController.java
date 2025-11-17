package com.fintrack.controller;

import com.fintrack.dto.DashboardDTO;
import com.fintrack.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<DashboardDTO> getDashboard(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        // Se não fornecido, usar o ano atual
        if (startDate == null) {
            startDate = LocalDate.now().withDayOfYear(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        DashboardDTO dashboard = dashboardService.getDashboard(userId, startDate, endDate);
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/user/{userId}/current-month")
    public ResponseEntity<DashboardDTO> getCurrentMonthDashboard(@PathVariable Long userId) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        
        DashboardDTO dashboard = dashboardService.getDashboard(userId, startDate, endDate);
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/user/{userId}/current-year")
    public ResponseEntity<DashboardDTO> getCurrentYearDashboard(@PathVariable Long userId) {
        LocalDate startDate = LocalDate.now().withDayOfYear(1);
        LocalDate endDate = LocalDate.now();
        
        DashboardDTO dashboard = dashboardService.getDashboard(userId, startDate, endDate);
        return ResponseEntity.ok(dashboard);
    }
} 