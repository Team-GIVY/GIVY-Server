package com.example.givy.domain.home.service.query;

import com.example.givy.domain.challenge.entity.StartChallenge;
import com.example.givy.domain.challenge.enums.Status;
import com.example.givy.domain.challenge.repository.ChallengeRepository;
import com.example.givy.domain.commerce.entity.Product;
import com.example.givy.domain.commerce.repository.ProductRepository;
import com.example.givy.domain.home.converter.HomeConverter;
import com.example.givy.domain.home.dto.res.HomeResDTO;
import com.example.givy.domain.recommendation.entity.RecommendationEvent;
import com.example.givy.domain.recommendation.entity.Tendency;
import com.example.givy.domain.recommendation.exception.TendencyException;
import com.example.givy.domain.recommendation.exception.code.TendencyErrorCode;
import com.example.givy.domain.recommendation.repository.RecommendationEventRepository;
import com.example.givy.domain.recommendation.repository.TendencyRepository;
import com.example.givy.domain.user.code.UserErrorCode;
import com.example.givy.domain.user.entity.Users;
import com.example.givy.domain.user.exception.UserException;
import com.example.givy.domain.user.repository.UserRepository;
import com.example.givy.domain.user.repository.UserSecuritiesAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeQueryServiceImpl implements HomeQueryService{

    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final TendencyRepository tendencyRepository;
    private final UserSecuritiesAccountRepository userSecuritiesAccountRepository;
    private final RecommendationEventRepository recommendationEventRepository;

    @Override
    public HomeResDTO.HomeResponseDTO getHome(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_ID_NOT_FOUND));

        Tendency tendency = tendencyRepository.findByUsers(user).orElseThrow(() -> new TendencyException(TendencyErrorCode.TENDENCY_NOT_FOUND));

        Optional<StartChallenge> challengeOpt = challengeRepository.findByUser(user);

        if (challengeOpt.isPresent()) {

            StartChallenge challenge = challengeOpt.get();
            Product product = challenge.getProduct();

            String securitiesName = userSecuritiesAccountRepository.findByUsers(user).map(account -> account.getSecuritiesName()).orElseThrow(() -> new UserException(UserErrorCode.USER_SECURITIES_ACCOUNT_NOT_FOUND));

            HomeResDTO.AfterHomeDTO afterData = HomeConverter.toAfterHomeDTO(user, product, securitiesName);
            return HomeConverter.toHomeResponseDTO(user, "AFTER_CHALLENGE", afterData);
        } else {
            RecommendationEvent event = recommendationEventRepository.findTopByTendencyOrderByCreatedAtDesc(tendency).orElseThrow(() -> new TendencyException(TendencyErrorCode.RECOMMENDATION_NOT_FOUND));

            Product bestProduct = event.getBestProduct();

            HomeResDTO.BeforeHomeDTO beforeDTO = HomeConverter.toBeforeHomeDTO(tendency, bestProduct);
            return HomeConverter.toHomeResponseDTO(user, "BEFORE_CHALLENGE", beforeDTO);
        }
    }
}
