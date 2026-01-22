package com.example.givy.domain.user.code;

import com.example.givy.global.apiPayLoad.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {

    USER_SIGNUP_CREATED(HttpStatus.CREATED, "USER201_1", "회원가입이 성공적으로 완료되었습니다."),
    USER_SOCIAL_SIGNUP_CREATED(HttpStatus.CREATED, "USER201_2", "프로필 완성하여 소셜 회원가입이 성공적으로 완료되었습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "USER200_1", "로그인이 성공적으로 완료되었습니다."),
    USER_FETCH_SUCCESS(HttpStatus.OK, "USER200_2", "유저 정보 조회 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
