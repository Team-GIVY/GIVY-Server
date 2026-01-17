package com.example.givy.domain.recommendation.entity;

import com.example.givy.domain.recommendation.dto.res.TendencyResDTO;
import com.example.givy.domain.user.entity.Users;
import com.example.givy.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name= "tendency")
public class Tendency extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tendencyId;

    @Column(name = "investment_type", nullable = false)
    private String investmentType;

    //금융 전속도
    @Column(name = "score_t", nullable = false)
    @Builder.Default
    private int scoreT = 1;

    //리스크 성향
    @Column(name = "score_r", nullable = false)
    @Builder.Default
    private int scoreR = 1;

    //유동 성향
    @Column(name = "score_l", nullable = false)
    @Builder.Default
    private int scoreL = 1;

    //3이 아니라 4 아닌가
    @Column(name = "total_score", nullable = false)
    @Builder.Default
    private int totalScore = 3;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    //mapping
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;

    @OneToMany(mappedBy="tendency")
    private List<RecommendationEvent> recommendationEvent = new ArrayList<>();

    public void update(TendencyResDTO.TendencyResultDTO result) {
        this.investmentType = result.investmentType();
        this.scoreT = result.scoreT();
        this.scoreR = result.scoreR();
        this.scoreL = result.scoreL();
        this.totalScore = result.totalScore();
        this.imageUrl = result.imageBasicUrl();
    }

}

