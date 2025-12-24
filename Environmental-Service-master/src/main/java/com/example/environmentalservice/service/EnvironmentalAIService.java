package com.example.environmentalservice.service;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentalAIService {
    @Autowired
        private OllamaChatModel chatModel;

        public String analyzeEnvironment(String area, int aqi) {
            if (aqi < 100) return "Air quality is good in " + area + ". Stay healthy!";

            String prompt = String.format(
                    "You are an Environmental AI. Area: %s, AQI: %d. " +
                            "Provide a 1-line health advisory. No intro.", area, aqi);

            try {
                return chatModel.call(prompt);
            } catch (Exception e) {
                return "High pollution detected. Wear a mask in " + area;
            }
        }
    }

