package com.fintrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("application", "FinTrack");
        response.put("version", "1.0.0");
        response.put("message", "FinTrack está funcionando corretamente!");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "FinTrack");
        response.put("description", "Sistema de Controle Financeiro Pessoal");
        response.put("author", "Isaac Figueiredo");
        response.put("github", "https://github.com/figueiredoisaac");
        response.put("linkedin", "https://www.linkedin.com/in/figueiredoisaac/");
        response.put("inspiration", "Pilar Financeiro - Notion");
        
        return ResponseEntity.ok(response);
    }
} 