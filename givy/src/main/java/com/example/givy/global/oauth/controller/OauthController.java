package com.example.givy.global.oauth.controller;

/*
카카오 로그인 시작시키기 (카카오 로그인 페이지로 redirect)
카카오 인증서버가 돌려준 code 받고 JWT 발급해주기.
 */

import com.example.givy.global.apiPayLoad.ApiResponse;
import com.example.givy.global.apiPayLoad.code.OauthSuccessCode;
import com.example.givy.global.oauth.model.KakaoUserInfo;
import com.example.givy.global.oauth.service.KakaoOauthService;
import com.example.givy.global.oauth.service.OauthUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {

    private final KakaoOauthService kakaoOauthService;
    private final OauthUserService oauthUserService;

    /**
     * 1) 카카오 로그인 시작
     *    클라이언트가 호출하면 → 카카오 로그인 페이지로 redirect됨
     */
    /* 01-03 카카오 로그인 API */
    @GetMapping("/kakao/login")
    public void redirectToKakao(HttpServletResponse response) throws IOException {
        response.sendRedirect(kakaoOauthService.generateKakaoLoginUrl());
    }


    /**
     * 2) 카카오 인증 후 redirect_uri로 code가 넘어오는 콜백 URL
     *    예) GET /oauth/kakao/callback?code=xxxx
     */
    @GetMapping("/kakao/callback")
    public ApiResponse<String> kakaoCallback(@RequestParam("code") String code) {

        // code → Kakao Token 요청 → UserInfo 요청
        KakaoUserInfo kakaoUser = kakaoOauthService.fetchKakaoUser(code);

        // User 생성/조회 → JWT 발급
        String jwt = oauthUserService.handleKakaoUser(kakaoUser);

        return ApiResponse.onSuccess(OauthSuccessCode.KAKAO_LOGIN_SUCCESS, jwt);
    }
}
