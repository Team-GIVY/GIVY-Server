package com.example.givy.domain.user.entity;

import com.example.givy.domain.user.enums.TargetType;
import com.example.givy.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    //default 값 넣는 게 좋지 않을까 생각합니다
    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;

    @Enumerated
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;

    //이동할 경로 ID
    @Column(name = "target_id")
    private Long targetId;
}
