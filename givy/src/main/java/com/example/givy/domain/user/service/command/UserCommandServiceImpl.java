package com.example.givy.domain.user.service.command;

import com.example.givy.domain.user.code.UserErrorCode;
import com.example.givy.domain.user.converter.UserConverter;
import com.example.givy.domain.user.dto.req.UserReqDTO;
import com.example.givy.domain.user.dto.res.UserResDTO;
import com.example.givy.domain.user.entity.Users;
import com.example.givy.domain.user.exception.UserException;
import com.example.givy.domain.user.repository.UserRepository;
import com.example.givy.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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


}
