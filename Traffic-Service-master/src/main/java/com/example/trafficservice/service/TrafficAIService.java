package com.example.trafficservice.service;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficAIService {
    @Autowired
        private OllamaChatModel chatModel;

        public String getTrafficAdvice(String area, int count) {

            if (count <= 100) {
                return "Traffic is smooth in " + area + ". No action required.";
            }

            OllamaOptions options = OllamaOptions.create()
                    .withModel("tinyllama")
                    .withTemperature(0.1f) // Deterministic output (strict)
                    .withTopP(0.4f);

            SystemMessage systemMessage = new SystemMessage(
                    "You are a Traffic Control AI for a Smart City. " +
                            "Task: Provide only 1 short diversion route. " +
                            "Rule: No introduction, no conversational filler, just the route."
            );

            UserMessage userMessage = new UserMessage(
                    String.format("Traffic congestion at %s with %d vehicles. Give one short diversion.", area, count)
            );

            Prompt prompt = new Prompt(List.of(systemMessage, userMessage), options);

            try {
                ChatResponse response = chatModel.call(prompt);
                String aiResult = response.getResult().getOutput().getContent();

                String cleanedAdvice = aiResult
                        .replaceAll("(?i)(Assistant:|Solution:|Response:|Route:)", "")
                        .trim();

                return cleanedAdvice.isEmpty() ? "Divert via the nearest bypass road." : cleanedAdvice;

            } catch (Exception e) {
                return "Heavy traffic! Divert via the nearest link road to save time.";
            }
        }
    }

