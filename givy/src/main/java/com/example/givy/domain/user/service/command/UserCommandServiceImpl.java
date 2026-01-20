package com.example.givy.domain.user.service.command;

import com.example.givy.domain.user.code.UserErrorCode;
import com.example.givy.domain.user.converter.UserConverter;
import com.example.givy.domain.user.dto.req.UserReqDTO;
import com.example.givy.domain.user.dto.req.UserSecuritiesReqDTO;
import com.example.givy.domain.user.dto.res.UserResDTO;
import com.example.givy.domain.user.dto.res.UserSecuritiesResDTO;
import com.example.givy.domain.user.entity.UserSecuritiesAccount;
import com.example.givy.domain.user.entity.Users;
import com.example.givy.domain.user.exception.UserException;
import com.example.givy.domain.user.repository.UserRepository;
import com.example.givy.domain.user.repository.UserSecuritiesRepository;
import com.example.givy.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserSecuritiesRepository userSecuritiesRepository;

    /* 01-01 회원가입 API */
    @Override
    public UserResDTO.UserInfoDTO signup(UserReqDTO.UserSignupDTO request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserException(UserErrorCode.USER_DUPLICATED_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Users user = UserConverter.toEntity(request, encodedPassword);

        Users savedUser = userRepository.save(user);

        return UserConverter.toDTO(savedUser);

    }

    /* 01-02 로그인 API */
    @Override
    public UserResDTO.UserLoginResDTO login(UserReqDTO.UserLoginDTO request) {
        Users user = userRepository.findByEmail(request.getEmail()) //null일 수도 있는 값을 감싸는 래퍼 타입 - Optional
                .orElseThrow(() -> new UserException(UserErrorCode.USER_EMAIL_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new UserException(UserErrorCode.USER_INVALID_PASSWORD);
        }

        String token = jwtTokenProvider.createToken(user.getUserId(), user.getRole());

        return UserConverter.toLoginDTO(token, user);
    }

    /* 01-04 소셜 회원가입(프로필 완성 단계) */
    @Override
    public void socialSignup(Long userId, UserReqDTO.UserProfileDTO dto){
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_ID_NOT_FOUND));

        //OauthUserService에서 카카오로 로그인한 유저 미리 하드코딩으로 NOTNULL 데이터들 넣어줬음. 다시 덮어씌우기.
        //이미 생성된 entity를 userId기준으로 찾았음.
        user.completeSocialProfile(dto);

        // db 저장 끝.
        userRepository.save(user);
    }

    /* 06-01 증권 계좌 등록 */
    @Override
    public UserSecuritiesResDTO.UserSecuritiesListDTO registerSecurities(Long userId, UserSecuritiesReqDTO.RegisterSecuritiesDTO dto) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_ID_NOT_FOUND));

        List<UserSecuritiesAccount> userSecuritiesEntity = UserConverter.toUserSecuritiesEntity(user, dto);

        List<UserSecuritiesAccount> saved = userSecuritiesRepository.saveAll(userSecuritiesEntity);

        return UserConverter.toUserSecuritiesListDTO(saved);
    }
}
