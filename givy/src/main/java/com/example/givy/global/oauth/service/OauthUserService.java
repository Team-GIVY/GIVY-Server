package com.example.givy.global.oauth.service;
/*
“카카오 사용자 정보를 우리 DB User와 매핑하고 JWT 발급” 책임
소셜 종류(KAKAO, GOOGLE, NAVER 등)가 늘어나도
이 서비스는 공통 “소셜 사용자 처리” 로 사용할 수 있음.
 */

import com.example.givy.domain.user.entity.Users;
import com.example.givy.domain.user.enums.Language;
import com.example.givy.domain.user.enums.Role;
import com.example.givy.domain.user.repository.UserRepository;
import com.example.givy.global.oauth.model.GoogleUserInfo;
import com.example.givy.global.oauth.model.KakaoUserInfo;
import com.example.givy.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String handleKakaoUser(KakaoUserInfo info){

        // 이메일 기준으로 유저 조회
        Users user = userRepository.findByEmail(info.getEmail())
                .orElseGet(() -> createKakaoUser(info));

        // JWT 발급
        return jwtTokenProvider.createToken(user.getUserId(), user.getRole());
    }

    private Users createKakaoUser(KakaoUserInfo info){

        String encodedPassword = passwordEncoder.encode("kakao_oauth_placeholder");

        Users user = Users.builder()
                .email(info.getEmail())
                .password(encodedPassword)
                .name("프로필을 완성해주세요")
                .nickname(info.getNickname())
                .role(Role.USER)
                .language(Language.KO)
                .build();

        return userRepository.save(user);
    }

    public String handleGoogleUser(GoogleUserInfo info){
        Users user = userRepository.findByEmail(info.getEmail())
                .orElseGet(() -> createGoogleUser(info));

        return jwtTokenProvider.createToken(user.getUserId(), user.getRole());
    }

    private Users createGoogleUser(GoogleUserInfo info){
        String encodedPassword = passwordEncoder.encode("google_oauth_placeholder");

        Users user = Users.builder()
                .email(info.getEmail())
                .password(encodedPassword)
                .name("프로필을 완성해주세요")
                .nickname(info.getNickname())
                .role(Role.USER)
                .language(Language.KO)
                .build();

        return userRepository.save(user);
    }
}
