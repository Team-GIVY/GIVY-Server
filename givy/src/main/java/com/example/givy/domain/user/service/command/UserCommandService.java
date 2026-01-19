package com.example.givy.domain.user.service.command;

import com.example.givy.domain.user.dto.req.UserReqDTO;
import com.example.givy.domain.user.dto.req.UserSecuritiesReqDTO;
import com.example.givy.domain.user.dto.res.UserResDTO;
import com.example.givy.domain.user.dto.res.UserSecuritiesResDTO;

public interface UserCommandService {
    UserResDTO.UserInfoDTO signup(UserReqDTO.UserSignupDTO request);
    UserResDTO.UserLoginResDTO login(UserReqDTO.UserLoginDTO request);

    UserSecuritiesResDTO.UserSecuritiesListDTO registerSecurities(Long userId, UserSecuritiesReqDTO.RegisterSecuritiesDTO dto);
}
