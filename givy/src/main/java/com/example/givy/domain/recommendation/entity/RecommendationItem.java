package com.example.givy.domain.recommendation.entity;

import com.example.givy.domain.commerce.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name="recommendation_item")
public class RecommendationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommendation_item_id")
    private Long recommendationItemId;

    @Column(name="created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    //mapping
    @ManyToOne
    @JoinColumn(name="recommendation_event_id")
    private RecommendationEvent recommendationEvent;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
