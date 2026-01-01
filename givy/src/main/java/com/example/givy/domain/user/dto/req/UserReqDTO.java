package com.example.givy.domain.user.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

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
        private String username;

    }
}
