package com.example.givy.domain.challenge.entity;

import com.example.givy.domain.challenge.enums.Status;
import com.example.givy.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="start_challenge")
public class StartChallenge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="start_challenge_id")
    private Long startChallengeId;

    @Column(name="status")
    private Status status;
}
