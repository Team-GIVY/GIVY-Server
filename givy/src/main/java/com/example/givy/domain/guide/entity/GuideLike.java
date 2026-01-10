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
@Table(name="guide_like")
public class GuideLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="guide_like_id")
    private Long guideLikeId;

    @Column(name="created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    //추가로 사용자가 좋아요 취소할 경우를 대비해서 idDeleted를 두고 따로 관리하는 건 어떨까 싶습니다
}
