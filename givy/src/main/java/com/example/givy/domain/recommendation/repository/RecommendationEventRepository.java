package com.example.givy.domain.recommendation.repository;

import com.example.givy.domain.recommendation.entity.RecommendationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationEventRepository extends JpaRepository<RecommendationEvent, Long> {
}
