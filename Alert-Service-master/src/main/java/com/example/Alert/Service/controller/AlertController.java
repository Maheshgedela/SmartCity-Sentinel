package com.example.Alert.Service.controller;

import com.example.Alert.Service.model.Alert;
import com.example.Alert.Service.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class AlertController {
    @Autowired
        private AlertRepository alertRepository;

        @PostMapping("/trigger")
        public ResponseEntity<?> triggerAlert(@RequestBody Alert alert) {
            alert.setTimestamp(LocalDateTime.now().toString());

            Alert savedAlert = alertRepository.save(alert);

            if ("HIGH".equalsIgnoreCase(savedAlert.getSeverity()) || "CRITICAL".equalsIgnoreCase(savedAlert.getSeverity())) {
                return ResponseEntity.ok().body("🚨 EMERGENCY ALERT SAVED: " + savedAlert.getAreaName());
            }

            return ResponseEntity.ok(savedAlert);
        }

        @GetMapping("/active")
        public ResponseEntity<List<Alert>> getAllAlerts() {
            List<Alert> alerts = alertRepository.findAll();
            return ResponseEntity.ok(alerts);
        }
    }


