package com.example.environmentalservice.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class EnvironmentalData {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String areaName;
        private Integer aqiValue; // Air Quality Index
        private Double temperature;
        private String co2Level;
        @Column(columnDefinition = "TEXT")
        private String aiImpactAnalysis; // AI suggestion about environment
        private LocalDateTime timestamp;
    }

