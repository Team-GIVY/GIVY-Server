package com.example.givy.domain.challenge.service.command;

import com.example.givy.domain.challenge.code.StampErrorCode;
import com.example.givy.domain.challenge.code.StartChallengeErrorCode;
import com.example.givy.domain.challenge.converter.StartChallengeConverter;
import com.example.givy.domain.challenge.dto.req.StartChallengeReqDTO;
import com.example.givy.domain.challenge.dto.res.StartChallengeResDTO;
import com.example.givy.domain.challenge.entity.Stamp;
import com.example.givy.domain.challenge.entity.StartChallenge;
import com.example.givy.domain.challenge.exception.StampException;
import com.example.givy.domain.challenge.exception.StartChallengeException;
import com.example.givy.domain.challenge.repository.StampRepository;
import com.example.givy.domain.challenge.repository.StartChallengeRepository;
import com.example.givy.domain.commerce.code.ProductErrorCode;
import com.example.givy.domain.commerce.entity.Product;
import com.example.givy.domain.commerce.exception.ProductException;
import com.example.givy.domain.commerce.repository.ProductRepository;
import com.example.givy.domain.user.code.UserErrorCode;
import com.example.givy.domain.user.converter.UserConverter;
import com.example.givy.domain.user.entity.UserStamp;
import com.example.givy.domain.user.entity.Users;
import com.example.givy.domain.user.exception.UserException;
import com.example.givy.domain.user.repository.UserRepository;
import com.example.givy.domain.user.repository.UserStampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StartChallengeCommandServiceImpl implements StartChallengeCommandService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StartChallengeRepository startChallengeRepository;
    private final StampRepository stampRepository;
    private final UserStampRepository userStampRepository;

    @Override
    public StartChallengeResDTO.StartChallengeInfoDTO joinChallenge(Long userId, StartChallengeReqDTO.joinChallengeDTO dto) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_ID_NOT_FOUND));

        Product product = productRepository.findByProductId(dto.getProductId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND_BY_PRODUCT_ID));

        StartChallenge challenge = StartChallengeConverter.toEntity(user, product);

        startChallengeRepository.save(challenge);

        return StartChallengeConverter.toStartChallengeInfoDTO(challenge);
    }

    @Override
    public StartChallengeResDTO.StartChallengeInfoDTO completeChallenge(Long userId, Long challengeId) {
        StartChallenge challenge = startChallengeRepository.findByUsers_UserId(userId)
                .orElseThrow(() -> new StartChallengeException(StartChallengeErrorCode.USER_CHALLENGE_NOT_FOUND));

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_ID_NOT_FOUND));

        //사용자 스탬프 부여
        Stamp stamp = stampRepository.findByName("스타트 챌린지 완료")
                .orElseThrow(() -> new StampException(StampErrorCode.STAMP_NOT_FOUND_BY_NAME));

        if (!userStampRepository.findByUsers_UserId(userId)) {
            UserStamp userStamp = UserConverter.toUserStampEntity(user, stamp);
            userStampRepository.save(userStamp);
        }

        challenge.updateStatus();
        challenge.updateStamp(stamp);

        return StartChallengeConverter.toStartChallengeInfoDTO(challenge);
    }
}
