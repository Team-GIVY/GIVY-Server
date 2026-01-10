package com.example.givy.domain.user.entity;

import com.example.givy.domain.user.enums.Language;
import com.example.givy.domain.user.enums.Role;
import com.example.givy.domain.user.enums.SocialType;
import com.example.givy.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "users")  //예약어 충돌 방지를 위한 테이블명 수정 user->users
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false, length = 10)
    @Size(min = 2, max = 10, message = "2자에서 10자 사이의 이름을 입력해 주세요.")
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣]*$",
            message = "이름에 특수 문자와 공백은 포함될 수 없습니다."
    )
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false, length = 64, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;

    @Column(name = "social_id")
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "securities_name")
    private String securitiesName;

    @Column(name = "is_securities_connected", nullable = false)
    @Builder.Default
    private Boolean isSecuritiesConnected = false;

    @Column(name = "connected_at")
    private LocalDateTime connectedAt;

    //erd 추가
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;
}
