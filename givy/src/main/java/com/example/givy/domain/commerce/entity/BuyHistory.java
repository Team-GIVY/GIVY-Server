package com.example.givy.domain.commerce.entity;

import com.example.givy.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="buy_history")
public class BuyHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="buy_history_id")
    private Long buyHistoryId;

    @Column(name="purchase_price", nullable = false)
    private Long purchasePrice;

    @Column(name="quantity", nullable = false)
    private Long quantity;
}