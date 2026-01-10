package com.example.givy.domain.user.converter;

import com.example.givy.domain.user.dto.req.UserReqDTO;
import com.example.givy.domain.user.dto.res.UserResDTO;
import com.example.givy.domain.user.entity.User;
import com.example.givy.domain.user.enums.Role;

public class UserConverter {

    //dto -> entity
    public static User toEntity(UserReqDTO.UserSignupDTO dto, String encodedPw) {
        return User.builder()
                .email(dto.getEmail())
                .password(encodedPw)
                .name(dto.getUsername())
                .role(Role.USER)
                .build();
    }


    //entity -> userInfoDto
    public static UserResDTO.UserInfoDTO toDTO(User user) {
        return UserResDTO.UserInfoDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getName())
                .build();
    }

    // 로그인 응답 DTO (token + userInfo)
    public static UserResDTO.UserLoginResDTO toLoginDTO(String token, User user) {
        return UserResDTO.UserLoginResDTO.builder()
                .token(token)
                .user(toDTO(user))
                .build();
    }

    public static UserResDTO.UserDetailDTO toDetailDTO(User user) {
        return UserResDTO.UserDetailDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getName())
                .role(user.getRole())
                .build();
    }


}
