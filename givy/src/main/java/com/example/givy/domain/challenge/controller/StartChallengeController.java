package com.example.givy.domain.challenge.controller;

import com.example.givy.domain.challenge.code.StartChallengeSuccessCode;
import com.example.givy.domain.challenge.dto.req.StartChallengeReqDTO;
import com.example.givy.domain.challenge.dto.res.StartChallengeResDTO;
import com.example.givy.domain.challenge.service.command.StartChallengeCommandService;
import com.example.givy.domain.challenge.service.query.StartChallengeQueryService;
import com.example.givy.domain.user.code.UserSuccessCode;
import com.example.givy.domain.user.dto.req.UserSecuritiesReqDTO;
import com.example.givy.domain.user.dto.res.UserResDTO;
import com.example.givy.domain.user.dto.res.UserSecuritiesResDTO;
import com.example.givy.domain.user.service.command.UserCommandService;
import com.example.givy.domain.user.service.query.UserQueryService;
import com.example.givy.global.apiPayLoad.ApiResponse;
import com.example.givy.global.security.CustomPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/start-challenge")
public class StartChallengeController implements StartChallengeControllerDocs {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final StartChallengeCommandService startChallengeCommandService;
    private final StartChallengeQueryService startChallengeQueryService;

    /* 06-01 증권 계좌 등록 */
    @PostMapping("/setup/securities")
    public ApiResponse<UserSecuritiesResDTO.UserSecuritiesListDTO> registerSecurities(
            @AuthenticationPrincipal CustomPrincipal principal,
            @RequestBody @Valid UserSecuritiesReqDTO.RegisterSecuritiesDTO dto
    ) {
        return ApiResponse.onSuccess(UserSuccessCode.USER_SECURITIES_CREATED, userCommandService.registerSecurities(principal.getUserId(), dto));
    }

    /* 06-02 성인 여부 판단 */
    @GetMapping("/setup/age-check")
    public ApiResponse<UserResDTO.AgeVerificationResDTO> verifyAdult(
            @AuthenticationPrincipal CustomPrincipal principal) {
        return ApiResponse.onSuccess(UserSuccessCode.USER_ADULT_CHECK_SUCCESS, userQueryService.verifyAdult(principal.getUserId()));
    }

    /* 06-03 스타트 챌린지 시작 */
    @PostMapping()
    public ApiResponse<StartChallengeResDTO.StartChallengeInfoDTO> joinChallenge(
            @AuthenticationPrincipal CustomPrincipal principal,
            @RequestBody @Valid StartChallengeReqDTO.joinChallengeDTO dto){
        return ApiResponse.onSuccess(StartChallengeSuccessCode.START_CHALLENGE_CREATED, startChallengeCommandService.joinChallenge(principal.getUserId(), dto));
    }

    /* 06-04 스타트 챌린지 완료 */
    @PatchMapping("/{startChallengeId}")
    public ApiResponse<StartChallengeResDTO.StartChallengeInfoDTO> completeChallenge(
            @PathVariable Long startChallengeId,
            @AuthenticationPrincipal CustomPrincipal principal
    ){
        return ApiResponse.onSuccess(StartChallengeSuccessCode.START_CHALLENGE_COMPLETED, startChallengeCommandService.completeChallenge(principal.getUserId(), startChallengeId));
    }

    /* 06-05 스타트 챌린지 상태 조회 */
    @GetMapping()
    public ApiResponse<StartChallengeResDTO.StartChallengeStatusDTO> getChallengeStatus(
            @AuthenticationPrincipal CustomPrincipal principal){

        StartChallengeResDTO.StartChallengeStatusDTO result = startChallengeQueryService.getChallengeStatus(principal.getUserId());

        if(result == null){
            return ApiResponse.onSuccess(StartChallengeSuccessCode.START_CHALLENGE_NOT_FOUND, null);
        }

        return ApiResponse.onSuccess(StartChallengeSuccessCode.START_CHALLENGE_STATUS_FOUND, result);
    }
}
