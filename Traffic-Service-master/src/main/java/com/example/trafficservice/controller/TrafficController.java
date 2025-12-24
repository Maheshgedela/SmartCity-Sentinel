package com.example.trafficservice.controller;

import com.example.trafficservice.model.TrafficData;
import com.example.trafficservice.repository.TrafficRepository;
import com.example.trafficservice.service.TrafficAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traffic")
public class TrafficController {
        @Autowired
        private TrafficRepository repository;

        @Autowired
        private TrafficAIService aiService;

        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        @PostMapping("/report")
        public ResponseEntity<TrafficData> saveTrafficData(@RequestBody TrafficData data) {
            data.setStatus(data.getVehicleCount() > 500 ? "HEAVY" : "NORMAL");

            String advice = aiService.getTrafficAdvice(data.getAreaName(), data.getVehicleCount());
            data.setAiRecommendation(advice);

            TrafficData savedData = repository.save(data);

            messagingTemplate.convertAndSend("/topic/traffic", savedData);

            return ResponseEntity.ok(savedData);
        }

        @GetMapping("/history")
        public ResponseEntity<List<TrafficData>> getTrafficHistory() {
            List<TrafficData> history = repository.findAll();
            return ResponseEntity.ok(history);
        }
    }



