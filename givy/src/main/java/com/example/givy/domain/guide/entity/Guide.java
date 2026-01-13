package com.example.givy.domain.guide.entity;

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
@Table(name = "guide")
public class Guide extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guide_id")
    private Long guideId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "text")
    private String body;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private Long likeCount = 0L;

    @Column(name = "store_count", nullable = false)
    @Builder.Default
    private Long storeCount = 0L;

    //카테고리 태그(ex. 주식초보, 절약팁)
    @Column(name = "category")
    private String category;

    //mapping
    @OneToMany(mappedBy="guide")
    private List<GuideLike> guideLike = new ArrayList<>();

    @OneToMany(mappedBy="guide")
    private List<GuideStore> guideStore =new ArrayList<>();
}
