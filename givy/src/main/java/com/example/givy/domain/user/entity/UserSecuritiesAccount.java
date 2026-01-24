package com.example.givy.domain.user.entity;

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
@Table(name="user_securities_account")
public class UserSecuritiesAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long accountId;

    @Column(name="securities_name", nullable = false)
    private String securitiesName;

    @Column(name="is_securities_connected", nullable = false)
    @Builder.Default
    private Boolean isSecuritiesConnected = false;

    @Column(name="connected_at")
    @CreatedDate
    private LocalDateTime connectedAt;

    //mapping
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;
}