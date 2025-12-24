package com.example.Alert.Service.service;

import com.example.Alert.Service.model.Alert;
import com.example.Alert.Service.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {
    @Autowired
        private AlertRepository alertRepository;

        public String saveAndProcessAlert(Alert alert) {
            alert.setTimestamp(LocalDateTime.now().toString());

            alertRepository.save(alert);

            if ("HIGH".equalsIgnoreCase(alert.getSeverity())) {
                System.out.println("🚨 EMERGENCY ACTION INITIATED FOR: " + alert.getAreaName());
                return "Emergency Alert Processed and Logged to MySQL!";
            }

            return "Alert logged successfully.";
        }

        public List<Alert> getAllAlerts() {
            return alertRepository.findAll();
        }
    }

