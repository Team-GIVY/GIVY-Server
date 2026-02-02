package com.example.givy.domain.user.dto.req;

import com.example.givy.domain.user.enums.Language;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class UserReqDTO {

    @Getter
    @Builder
    public static class UserLoginDTO {

        @NotNull
        @Email
        private String email;

        @NotNull
        private String password;
    }

    @Getter
    @Builder
    public static class UserSignupDTO {

        @NotNull
        @Email
        private String email;

        @NotNull
        private String password;

        @NotNull
        private String name;

        @NotNull
        private String nickname;

        @NotNull
        private Language language;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

    }

    @Getter
    @Builder
    public static class UserProfileDTO{

        @NotNull
        private String name;

        @NotNull
        private String nickname;

        @NotNull
        private Language language;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @NotNull
        private String profileImageUrl;

    }

    @Getter
    @Builder
    public static class UserLogoutDTO{
        @NotNull
        private String refreshToken;
    }

    @Getter
    @Builder
    public static class UserTokenRefreshDTO {

        @NotNull
        private String refreshToken;
    }


}
