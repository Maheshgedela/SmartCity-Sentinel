package com.example.AI_Engine.Service.service;

import com.example.AI_Engine.Service.model.AIResponse;
import com.example.AI_Engine.Service.repository.AIRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIEngineService {
    private final ChatClient chatClient;

        @Autowired
        private AIRepository repository;

        @Autowired
        private RestTemplate restTemplate;

        private final ObjectMapper objectMapper = new ObjectMapper();

        public AIEngineService(ChatClient.Builder builder) {
            this.chatClient = builder.build();
        }

        public String analyzeCityData(String area, int count, int aqi) {
            // 1. Strict Short Prompt: AI confusion thagginchadaniki
            String prompt = String.format(
                    "SYSTEM: ACT AS SMART CITY AI. RETURN ONLY JSON. NO EXPLANATION. NO PYTHON.\n" +
                            "INPUT: Area:%s, Vehicles:%d, AQI:%d\n" +
                            "FORMAT: {\"prediction\": \"...\", \"alertLevel\": \"HIGH/MEDIUM/LOW\", \"healthAdvice\": \"...\"}",
                    area, count, aqi);

            OllamaOptions options = OllamaOptions.create()
                    .withModel("tinyllama")
                    .withTemperature(0.1);

            String aiResult;
            try {
                // AI Call
                aiResult = chatClient.prompt(prompt)
                        .options(options)
                        .call()
                        .content();

                // 2. Cleaning: Extract only the JSON part from AI response
                if (aiResult.contains("{") && aiResult.contains("}")) {
                    aiResult = aiResult.substring(aiResult.indexOf("{"), aiResult.lastIndexOf("}") + 1);
                }
            } catch (Exception e) {
                System.err.println("AI Call Error: " + e.getMessage());
                aiResult = "FALLBACK";
            }

            AIResponse history = new AIResponse();
            history.setAreaName(area);
            history.setVehicleCount(count);
            history.setRecordedAqi(aqi);
            history.setTimestamp(LocalDateTime.now());

            // 3. JSON Parsing & Fallback Logic
            try {
                String cleanedJson = aiResult.replaceAll("```json|```", "").trim();
                JsonNode jsonNode = objectMapper.readTree(cleanedJson);

                history.setPrediction(jsonNode.get("prediction").asText());
                history.setAlertLevel(jsonNode.get("alertLevel").asText().toUpperCase());
                history.setHealthAdvice(jsonNode.get("healthAdvice").asText());

            } catch (Exception e) {
                System.err.println("Parsing failed, applying safety rules...");
                history.setPrediction("Data analyzed by system rules.");

                // Manual Logic if AI fails to give JSON
                if (aqi > 250 || count > 500) {
                    history.setAlertLevel("HIGH");
                    history.setHealthAdvice("High pollution/traffic! Use bypass roads and wear masks.");
                } else if (aqi > 100) {
                    history.setAlertLevel("MEDIUM");
                    history.setHealthAdvice("Moderate pollution. Sensitive people should stay indoors.");
                } else {
                    history.setAlertLevel("LOW");
                    history.setHealthAdvice("Conditions are normal.");
                }
            }

            // 4. Trigger Alert Service if status is HIGH
            if ("HIGH".equalsIgnoreCase(history.getAlertLevel())) {
                triggerEmergencyAlert(history);
            }

            repository.save(history);

            return String.format("{\"prediction\":\"%s\", \"alertLevel\":\"%s\", \"healthAdvice\":\"%s\"}",
                    history.getPrediction(), history.getAlertLevel(), history.getHealthAdvice());
        }

        private void triggerEmergencyAlert(AIResponse history) {
            try {
                Map<String, Object> alertPayload = new HashMap<>();
                alertPayload.put("areaName", history.getAreaName());
                alertPayload.put("alertType", "AI_EMERGENCY");
                alertPayload.put("severity", "HIGH");
                alertPayload.put("message", history.getHealthAdvice());

                restTemplate.postForObject(
                        "http://ALERT-SERVICE/api/alerts/trigger",
                        alertPayload,
                        String.class);
                System.out.println("Alert sent to ALERT-SERVICE successfully.");
            } catch (Exception e) {
                System.err.println("ALERT-SERVICE integration failed: " + e.getMessage());
            }
        }

        public List<AIResponse> getAllHistory() {
            return repository.findAll();
        }
    }



