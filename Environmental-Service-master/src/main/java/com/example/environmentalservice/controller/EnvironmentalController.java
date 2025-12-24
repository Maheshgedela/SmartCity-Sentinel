package com.example.environmentalservice.controller;

import com.example.environmentalservice.model.EnvironmentalData;
import com.example.environmentalservice.repository.EnvironmentalRepository;
import com.example.environmentalservice.service.EnvironmentalAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/environment")
public class EnvironmentalController {
    @Autowired
        private EnvironmentalRepository repository;

        @Autowired
        private EnvironmentalAIService aiService;

        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        // 1. POST: Pollution data report chesi AI analysis save chestundi
        @PostMapping("/report")
        public EnvironmentalData reportPollution(@RequestBody EnvironmentalData data) {
            data.setTimestamp(LocalDateTime.now());

            // Null Check for AQI Value - Crash avvakunda handle chestundi
            if (data.getAqiValue() != null) {
                data.setCo2Level(data.getAqiValue() > 200 ? "HIGH" : "NORMAL");

                // AI Analysis calling
                String impact = aiService.analyzeEnvironment(data.getAreaName(), data.getAqiValue());
                data.setAiImpactAnalysis(impact);
            } else {
                data.setCo2Level("UNKNOWN");
                data.setAiImpactAnalysis("No AQI data provided");
            }

            // Database lo save chestundi
            EnvironmentalData savedData = repository.save(data);

            // WebSocket dwara Dashboard ki realtime ga update pampisthundi
            messagingTemplate.convertAndSend("/topic/environment", savedData);

            return savedData;
        }

        // 2. GET: Save ayina records anni chudataniki
        @GetMapping("/history")
        public ResponseEntity<List<EnvironmentalData>> getEnvironmentalHistory() {
            List<EnvironmentalData> history = repository.findAll();
            return ResponseEntity.ok(history);
        }
    }

