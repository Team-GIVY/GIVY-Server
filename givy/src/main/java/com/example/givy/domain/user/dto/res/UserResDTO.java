package com.example.givy.domain.user.dto.res;

import com.example.givy.domain.user.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class UserResDTO {

    @Getter
    @Builder
    public static class UserInfoDTO{

        @NotNull
        private Long userId;

        @NotNull
        private String username;

        @Email
        @NotNull
        private String email;

    }
    @Getter
    @Builder
    public static class UserDetailDTO{

        @NotNull
        private Long userId;

        @NotNull
        private String username;

        @Email
        @NotNull
        private String email;

        @NotNull
        private Role role;

    }

    @Getter
    @Builder
    public static class UserLoginResDTO {
        private String token;              // JWT 액세스 토큰
        private UserInfoDTO user;          // 로그인한 유저 기본 정보
    }

    @Getter
    @Builder
    public static class AgeVerificationResDTO{
        private Long userId;
        private LocalDate birth;
        private boolean isAdult;
    }
}
