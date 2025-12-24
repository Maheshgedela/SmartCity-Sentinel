package com.example.environmentalservice.repository;

import com.example.environmentalservice.model.EnvironmentalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentalRepository extends JpaRepository<EnvironmentalData,Long> {
}
