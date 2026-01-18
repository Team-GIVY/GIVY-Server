package com.example.givy.domain.recommendation.service.command;

import com.example.givy.domain.recommendation.converter.TendencyConverter;
import com.example.givy.domain.recommendation.dto.req.TendencyReqDTO;
import com.example.givy.domain.recommendation.dto.res.TendencyResDTO;
import com.example.givy.domain.recommendation.entity.Tendency;
import com.example.givy.domain.recommendation.enums.InvestmentType;
import com.example.givy.domain.recommendation.repository.TendencyRepository;
import com.example.givy.domain.user.code.UserErrorCode;
import com.example.givy.domain.user.entity.Users;
import com.example.givy.domain.user.exception.UserException;
import com.example.givy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TendencyCommandServiceImpl implements TendencyCommandService{

    private final TendencyRepository tendencyRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TendencyResDTO.TendencyResultDTO submitTendency(Long userId, TendencyReqDTO.TendencySurveyDTO request) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_ID_NOT_FOUND));

        TendencyResDTO.TendencyResultDTO result = calculateTendency(request);

        Tendency savedTendency = tendencyRepository.findByUsers(user)
                .map(existingTendency -> {
                    existingTendency.update(result);
                    return existingTendency;
                })
                .orElseGet(() -> {
                    Tendency newTendency = TendencyConverter.toTendency(user, result);
                    return tendencyRepository.save(newTendency);
                });

        return TendencyConverter.toTendencyResultDTO(savedTendency);
    }

    private TendencyResDTO.TendencyResultDTO calculateTendency(TendencyReqDTO.TendencySurveyDTO request) {
        List<String> answers = request.survey();

        int scoreR = getScore(answers.get(0)) + getScore(answers.get(1));
        int scoreL = getScore(answers.get(2)) + getScore(answers.get(3));
        int scoreT = getScore(answers.get(4)) + getScore(answers.get(5));

        int totalScore = scoreR + scoreL + scoreT;

        String investmentType = determineInvestmentType(scoreR, scoreL, scoreT);

        String imageUrl = getImageUrl(investmentType);

        TendencyResDTO.TendencyResultDTO resultDTO = TendencyConverter.toTendencyResDTO(scoreR, scoreL, scoreT, totalScore, investmentType, imageUrl);

        return resultDTO;
    }

    private String getImageUrl(String type) {
        InvestmentType investmentType = InvestmentType.findByDescription(type);
        String fileName = switch (investmentType){
            case THEME -> "img_charater_bankbook_default.png";
            case MARKET -> "img_charater_card_default.png";
            case PARKING -> "img_charater_coin_default.png";
            case ASSET, FUND -> "img_charater_paper-money_default.png";
        };

        return "/images/" + fileName;
    }

    private int getScore(String answer) {
        if (answer == null) return 0;
        return switch (answer.toUpperCase()){
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            default -> 0;
        };
    }

    private String determineInvestmentType(int r, int l, int t) {
        // 안정추구
        if (r <= 2 || (r == 3 && t <= 3)){
            if (l <= 3){ // 단기
                return (t <= 4) ? InvestmentType.PARKING.getDescription() : InvestmentType.ASSET.getDescription();
            } else if (l == 4) { // 중기
                return (t <= 2) ? InvestmentType.PARKING.getDescription() : (t <= 4 ? InvestmentType.ASSET.getDescription() : InvestmentType.FUND.getDescription());
            } else { // 장기
                return (t <= 2) ? InvestmentType.ASSET.getDescription() : (t <= 4 ? InvestmentType.FUND.getDescription() : InvestmentType.ASSET.getDescription());
            }
        }

        else if (r <= 5){ // 위험선호
            if (l <= 3){ // 단기
                return (t <= 2) ? InvestmentType.PARKING.getDescription() : (t <= 4 ? InvestmentType.MARKET.getDescription() : InvestmentType.ASSET.getDescription());
            } else if (l == 4) { // 중기
                return (t <= 2) ? InvestmentType.PARKING.getDescription() : (t <= 4 ? InvestmentType.FUND.getDescription() : InvestmentType.ASSET.getDescription());
            } else { // 장기
                return (t <= 2) ? InvestmentType.FUND.getDescription() : (t <= 4 ? InvestmentType.ASSET.getDescription() : InvestmentType.MARKET.getDescription());
            }
        }

        else{ // 직접참여
            if (l <= 3){ // 단기
                return (t <= 2) ? InvestmentType.PARKING.getDescription() : (t <= 4 ? InvestmentType.ASSET.getDescription() : InvestmentType.MARKET.getDescription());
            } else if (l == 4) { // 중기
                return (t <= 2) ? InvestmentType.ASSET.getDescription() : (t <= 4 ? InvestmentType.MARKET.getDescription() : InvestmentType.THEME.getDescription());
            } else { // 장기
                return (t <= 2) ? InvestmentType.ASSET.getDescription() : (t <= 4 ? InvestmentType.MARKET.getDescription() : InvestmentType.THEME.getDescription());
            }
        }
    }
}
