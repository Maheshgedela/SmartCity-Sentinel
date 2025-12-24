package com.example.trafficservice.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
public class TrafficData {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String areaName;
        private Integer vehicleCount;
        private String status;

        @Column(columnDefinition = "TEXT")
        private String aiRecommendation;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getAreaName() { return areaName; }
        public void setAreaName(String areaName) { this.areaName = areaName; }

        public Integer getVehicleCount() { return vehicleCount; }
        public void setVehicleCount(Integer vehicleCount) { this.vehicleCount = vehicleCount; }

        public String getAiRecommendation() {
            return aiRecommendation;
        }

        public void setAiRecommendation(String aiRecommendation) {
            this.aiRecommendation = aiRecommendation;
        }


    // Getter and Setter
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    }