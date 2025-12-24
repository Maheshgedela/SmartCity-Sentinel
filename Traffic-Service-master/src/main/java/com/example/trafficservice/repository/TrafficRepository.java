package com.example.trafficservice.repository;

import com.example.trafficservice.model.TrafficData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficRepository extends JpaRepository<TrafficData,Long> {

}
