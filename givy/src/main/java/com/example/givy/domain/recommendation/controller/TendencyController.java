package com.example.givy.domain.recommendation.controller;

import com.example.givy.domain.recommendation.dto.req.TendencyReqDTO;
import com.example.givy.domain.recommendation.dto.res.TendencyResDTO;
import com.example.givy.domain.recommendation.exception.code.TendencySuccessCode;
import com.example.givy.domain.recommendation.service.command.TendencyCommandService;
import com.example.givy.global.apiPayLoad.ApiResponse;
import com.example.givy.global.security.CustomPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tendency")
public class TendencyController {

    private final TendencyCommandService tendencyCommandService;

    @PostMapping
    public ApiResponse<TendencyResDTO.TendencyResultDTO> submitTendency(@AuthenticationPrincipal CustomPrincipal principal, @RequestBody @Valid TendencyReqDTO.TendencySurveyDTO request){

        Long userId = principal.getUserId();

        TendencyResDTO.TendencyResultDTO result = tendencyCommandService.submitTendency(userId, request);

        return ApiResponse.onSuccess(TendencySuccessCode.TENDENCY_SURVEY_SUCCESS, result);
    }
}
