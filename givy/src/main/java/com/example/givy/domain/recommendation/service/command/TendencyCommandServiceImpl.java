package com.example.givy.domain.recommendation.service.command;

import com.example.givy.domain.recommendation.converter.TendencyConverter;
import com.example.givy.domain.recommendation.dto.req.TendencyReqDTO;
import com.example.givy.domain.recommendation.dto.res.TendencyResDTO;
import com.example.givy.domain.recommendation.entity.Tendency;
import com.example.givy.domain.recommendation.enums.InvestmentType;
import com.example.givy.domain.recommendation.repository.TendencyRepository;
import com.example.givy.domain.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TendencyCommandServiceImpl implements TendencyCommandService{

    private final TendencyRepository tendencyRepository;

    @Override
    @Transactional
    public TendencyResDTO.TendencyResultDTO submitTendency(Users user, TendencyReqDTO.TendencySurveyDTO request) {

        TendencyResDTO.TendencyResultDTO result = calculateTendency(request);

        tendencyRepository.findByUsers(user).ifPresentOrElse(
                existingTendency -> existingTendency.update(result),
                () -> {
                    Tendency newTendency = TendencyConverter.toTendency(user, result);
                    tendencyRepository.save(newTendency);
                }
        );

        return result;
    }

    private TendencyResDTO.TendencyResultDTO calculateTendency(TendencyReqDTO.TendencySurveyDTO request) {
        List<String> answers = request.survey();

        int scoreR = getScore(answers.get(0)) + getScore(answers.get(1));
        int scoreL = getScore(answers.get(2)) + getScore(answers.get(3));
        int scoreT = getScore(answers.get(4)) + getScore(answers.get(5));

        int totalScore = scoreR + scoreL + scoreT;

        String investmentType = determineInvestmentType(scoreR, scoreL, scoreT);

        String imageUrl = getImageUrl(investmentType);

        String riskLabel = getRiskLabel(scoreR, scoreT);
        String periodLabel = getPeriodLabel(scoreL);
        String familiarityLabel = getFamiliarityLabel(scoreT);

        TendencyResDTO.TendencyResultDTO resultDTO = TendencyConverter.toTendencyResDTO(scoreR, scoreL, scoreT, totalScore, investmentType, imageUrl, riskLabel, periodLabel, familiarityLabel);

        return resultDTO;
    }

    private String getRiskLabel(int r, int t) {
        if (r <= 2 || (r == 3 && t <= 3)) {
            return "안정추구";
        } else if (r <= 5) {
            return "위험선호";
        } else {
            return "직접참여";
        }
    }

    private String getPeriodLabel(int l) {
        if (l <= 3) return "단기";
        if (l == 4) return "중기";
        return "장기";
    }

    private String getFamiliarityLabel(int t) {
        if (t <= 2) return "안정형";
        if (t <= 4) return "중립형";
        return "공격형";
    }

    private String getImageUrl(String type) {
        InvestmentType investmentType = InvestmentType.valueOf(type);
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
                return (t <= 4) ? InvestmentType.PARKING.getValue() : InvestmentType.ASSET.getValue();
            } else if (l == 4) { // 중기
                return (t <= 2) ? InvestmentType.PARKING.getValue() : (t <= 4 ? InvestmentType.ASSET.getValue() : InvestmentType.FUND.getValue());
            } else { // 장기
                return (t <= 2) ? InvestmentType.ASSET.getValue() : (t <= 4 ? InvestmentType.FUND.getValue() : InvestmentType.ASSET.getValue());
            }
        }

        else if (r <= 5){ // 위험선호
            if (l <= 3){ // 단기
                return (t <= 2) ? InvestmentType.PARKING.getValue() : (t <= 4 ? InvestmentType.MARKET.getValue() : InvestmentType.ASSET.getValue());
            } else if (l == 4) { // 중기
                return (t <= 2) ? InvestmentType.PARKING.getValue() : (t <= 4 ? InvestmentType.FUND.getValue() : InvestmentType.ASSET.getValue());
            } else { // 장기
                return (t <= 2) ? InvestmentType.FUND.getValue() : (t <= 4 ? InvestmentType.ASSET.getValue() : InvestmentType.MARKET.getValue());
            }
        }

        else{ // 직접참여
            if (l <= 3){ // 단기
                return (t <= 2) ? InvestmentType.PARKING.getValue() : (t <= 4 ? InvestmentType.ASSET.getValue() : InvestmentType.MARKET.getValue());
            } else if (l == 4) { // 중기
                return (t <= 2) ? InvestmentType.ASSET.getValue() : (t <= 4 ? InvestmentType.MARKET.getValue() : InvestmentType.THEME.getValue());
            } else { // 장기
                return (t <= 2) ? InvestmentType.ASSET.getValue() : (t <= 4 ? InvestmentType.MARKET.getValue() : InvestmentType.THEME.getValue());
            }
        }
    }
}
