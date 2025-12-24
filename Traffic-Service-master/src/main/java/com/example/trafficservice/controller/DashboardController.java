package com.example.trafficservice.controller;

import com.example.trafficservice.model.TrafficData;
import com.example.trafficservice.repository.TrafficRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {
    @Autowired
        private TrafficRepository repository;

        @GetMapping("/dashboard")
        public String getDashboard(Model model) {
            // 1. Database nundi traffic data teeskuntunnam
            List<TrafficData> allData = repository.findAll();
            if (allData == null) {
                allData = new ArrayList<>();
            }
            model.addAttribute("allTrafficData", allData);

            // 2. Graph Labels (Null check add chesa, error raakunda)
            List<String> labels = allData.stream()
                    .map(d -> d.getAreaName() != null ? d.getAreaName() : "Unknown")
                    .collect(Collectors.toList());
            model.addAttribute("chartLabels", labels);

            // 3. Graph Values
            List<Integer> values = allData.stream()
                    .map(d -> d.getVehicleCount() != null ? d.getVehicleCount() : 0)
                    .collect(Collectors.toList());
            model.addAttribute("chartValues", values);

            // 4. Environment Dummy Data (Meeru adigina logic)
            List<Map<String, Object>> dummyEnvData = new ArrayList<>();

            Map<String, Object> env1 = new HashMap<>();
            env1.put("location", "Hitech City");
            env1.put("aqi", 75);
            env1.put("status", "Good");
            dummyEnvData.add(env1);

            Map<String, Object> env2 = new HashMap<>();
            env2.put("location", "Madhapur");
            env2.put("aqi", 120);
            env2.put("status", "Moderate");
            dummyEnvData.add(env2);

            model.addAttribute("allEnvData", dummyEnvData);

            // NOTE: File name templates folder lo 'traffic_dashboard.html' ani undali
            return "traffic-dashboard";
        }
    }

