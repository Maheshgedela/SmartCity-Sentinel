package com.example.AI_Engine.Service.repository;

import com.example.AI_Engine.Service.model.AIResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIRepository extends JpaRepository<AIResponse,Long> {

}
