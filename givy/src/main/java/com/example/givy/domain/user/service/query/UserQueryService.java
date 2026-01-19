package com.example.givy.domain.user.service.query;

import com.example.givy.domain.user.dto.res.UserResDTO;

public interface UserQueryService {
    UserResDTO.UserDetailDTO getUserInfo(Long userId);
    UserResDTO.AgeVerificationResDTO verifyAdult(Long userId);
}
