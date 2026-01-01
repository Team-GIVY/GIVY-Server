package com.example.givy.domain.user.controller;

import com.example.givy.domain.user.code.UserSuccessCode;
import com.example.givy.domain.user.dto.req.UserReqDTO;
import com.example.givy.domain.user.dto.res.UserResDTO;
import com.example.givy.domain.user.service.command.UserCommandService;
import com.example.givy.domain.user.service.query.UserQueryService;
import com.example.givy.global.apiPayLoad.ApiResponse;
import com.example.givy.global.security.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController implements UserControllerDocs {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    /* 01-01 회원가입 API */
    @PostMapping("/signup")
    public ApiResponse<UserResDTO.UserInfoDTO> SignUp(
            @RequestBody UserReqDTO.UserSignupDTO request
    ){
        return ApiResponse.onSuccess(UserSuccessCode.USER_SIGNUP_CREATED, userCommandService.signup(request));
    }

    /* 01-02 로그인 API */
    @PostMapping("/login")
    public ApiResponse<UserResDTO.UserLoginResDTO> Login(
        @RequestBody UserReqDTO.UserLoginDTO request
    ){
        return ApiResponse.onSuccess(UserSuccessCode.USER_LOGIN_SUCCESS, userCommandService.login(request));
    }

    /* 01-03 내 정보 조회 API */
    @GetMapping("/me")
    public ApiResponse<UserResDTO.UserDetailDTO> getUserInfo(
            @AuthenticationPrincipal CustomPrincipal principal
    ){
        UserResDTO.UserDetailDTO response = userQueryService.getUserInfo(principal.getUserId());
        return ApiResponse.onSuccess(UserSuccessCode.USER_FETCH_SUCCESS, response);
    }


}
