package com.example.givy.domain.user.service.command;

import com.example.givy.domain.user.dto.req.UserReqDTO;
import com.example.givy.domain.user.dto.res.UserResDTO;

public interface UserCommandService {
    UserResDTO.UserInfoDTO signup(UserReqDTO.UserSignupDTO request);
    UserResDTO.UserLoginResDTO login(UserReqDTO.UserLoginDTO request);

    /* 01-04 소셜 회원가입(프로필 완성 단계) */
    void socialSignup(Long userId, UserReqDTO.UserProfileDTO dto);
}
