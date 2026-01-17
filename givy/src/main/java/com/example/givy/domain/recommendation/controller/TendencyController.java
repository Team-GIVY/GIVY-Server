package com.example.givy.domain.recommendation.controller;

import com.example.givy.domain.recommendation.dto.req.TendencyReqDTO;
import com.example.givy.domain.recommendation.dto.res.TendencyResDTO;
import com.example.givy.domain.recommendation.exception.code.TendencySuccessCode;
import com.example.givy.domain.recommendation.service.command.TendencyCommandService;
import com.example.givy.domain.recommendation.service.query.TendencyQueryService;
import com.example.givy.domain.user.code.UserErrorCode;
import com.example.givy.domain.user.entity.Users;
import com.example.givy.domain.user.exception.UserException;
import com.example.givy.domain.user.repository.UserRepository;
import com.example.givy.global.apiPayLoad.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tendency")
public class TendencyController {

    private final TendencyCommandService tendencyCommandService;
    private final TendencyQueryService tendencyQueryService;

    @PostMapping
    public ApiResponse<TendencyResDTO.TendencyResultDTO> submitTendency(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid TendencyReqDTO.TendencySurveyDTO request){

        Long userId = Long.valueOf(userDetails.getUsername());

        TendencyResDTO.TendencyResultDTO result = tendencyCommandService.submitTendency(userId, request);

        return ApiResponse.onSuccess(TendencySuccessCode.TENDENCY_SURVEY_SUCCESS, result);
    }

    @GetMapping("/me")
    public ApiResponse<TendencyResDTO.TendencyViewDTO> getMyQuery(@AuthenticationPrincipal UserDetails userDetails) {

        Long userId = Long.valueOf(userDetails.getUsername());

        TendencyResDTO.TendencyViewDTO result = tendencyQueryService.getMyTendency(userId);

        return ApiResponse.onSuccess(TendencySuccessCode.TENDENCY_QUERY_SUCCESS, result);
    }
}
