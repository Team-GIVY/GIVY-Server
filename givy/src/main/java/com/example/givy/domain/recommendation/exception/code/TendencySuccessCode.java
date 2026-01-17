package com.example.givy.domain.recommendation.exception.code;

import com.example.givy.global.apiPayLoad.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TendencySuccessCode implements BaseSuccessCode {

    TENDENCY_SURVEY_SUCCESS(HttpStatus.CREATED, "TENDENCY201_1", "성향 조사가 성공적으로 완료되었습니다."),
    TENDENCY_QUERY_SUCCESS(HttpStatus.OK, "TENDENCY200_1", "성향 조회가 성공적으로 완료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
