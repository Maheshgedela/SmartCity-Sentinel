package com.example.AI_Engine.Service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String areaName;
    @Column(columnDefinition = "TEXT")
        private String prediction;
        private String alertLevel;
    @Column(columnDefinition = "TEXT")
        private String healthAdvice;
        private int recordedAqi;
        private int vehicleCount;
    private LocalDateTime timestamp;
    }

