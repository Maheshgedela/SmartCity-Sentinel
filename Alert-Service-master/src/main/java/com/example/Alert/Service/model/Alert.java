package com.example.Alert.Service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emergency_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String areaName;
        private String alertType;
        private String severity; // HIGH, MEDIUM, LOW
        private String message;
        private String timestamp;
    }

