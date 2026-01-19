package com.example.givy.domain.challenge.code;

import com.example.givy.global.apiPayLoad.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StartChallengeErrorCode implements BaseErrorCode {
    USER_CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHALLENGE404_1", "사용자의 챌린지 참여 정보를 확인할 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
