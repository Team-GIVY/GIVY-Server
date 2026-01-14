package com.example.givy.domain.recommendation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name="recommendation_event")
public class RecommendationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommendation_event_id")
    private Long recommendationEventId;

    @Column(name="recommendation_type", nullable = false)
    private String recommendationType;

    @Column(name="matched_step", nullable = false)
    private int matchedStep;

    @Column(name="created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    //mapping
    @ManyToOne
    @JoinColumn(name="tendency_id")
    private Tendency tendency;

    @OneToMany(mappedBy="recommendationEvent")
    private List<RecommendationItem> recommendationItem = new ArrayList<>();
}
