package com.example.givy.domain.user.entity;

import com.example.givy.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user_asset")
public class Asset extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_asset_id")
    private Long assetId;

    @Column(name="quantity")
    private Long quantity;

    @Column(name="average_price")
    private Double averagePrice;
}
