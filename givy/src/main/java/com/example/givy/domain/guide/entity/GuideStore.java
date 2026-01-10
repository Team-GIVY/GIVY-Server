package com.example.givy.domain.guide.entity;

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
@Table(name="guide_store")
public class GuideStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="guide_store_id")
    private Long guideStoreId;

    @Column(name="created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    //GuideLike와 마찬가지로 isDeleted
}
