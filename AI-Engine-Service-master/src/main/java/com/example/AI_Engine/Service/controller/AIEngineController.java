package com.example.AI_Engine.Service.controller;

import com.example.AI_Engine.Service.model.AIResponse;
import com.example.AI_Engine.Service.service.AIEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ai")
public class AIEngineController {
    @Autowired
        private AIEngineService aiEngineService;


        @PostMapping("/analyze")
        public String getSmartAdvice(@RequestBody Map<String, Object> cityData) {
            String areaName = (String) cityData.get("areaName");
            int vehicleCount = (int) cityData.get("vehicleCount");
            int aqiValue = (int) cityData.get("aqiValue");

            return aiEngineService.analyzeCityData(areaName, vehicleCount, aqiValue);
        }
        @GetMapping("/history")
        public List<AIResponse> getHistory() {
            return aiEngineService.getAllHistory();
        }
    }

