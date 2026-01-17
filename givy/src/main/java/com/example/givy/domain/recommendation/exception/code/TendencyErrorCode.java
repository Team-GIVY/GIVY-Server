package com.example.givy.domain.recommendation.exception.code;

import com.example.givy.global.apiPayLoad.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TendencyErrorCode implements BaseErrorCode {

    TENDENCY_NOT_FOUND(HttpStatus.NOT_FOUND, "TENDENCY404_1", "존재하지 않는 성향 정보입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}