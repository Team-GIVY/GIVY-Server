package com.example.givy.domain.recommendation.converter;

import com.example.givy.domain.recommendation.dto.res.TendencyResDTO;
import com.example.givy.domain.recommendation.entity.Tendency;
import com.example.givy.domain.user.entity.Users;

public class TendencyConverter {

    public static Tendency toTendency(Users users, TendencyResDTO.TendencyResultDTO resultDTO){
        return Tendency.builder()
                .investmentType(resultDTO.investmentType())
                .scoreR(resultDTO.scoreR())
                .scoreL(resultDTO.scoreL())
                .scoreT(resultDTO.scoreT())
                .totalScore(resultDTO.totalScore())
                .imageUrl(resultDTO.imageBasicUrl())
                .users(users)
                .build();
    }

    public static TendencyResDTO.TendencyResultDTO toTendencyResDTO(int scoreR, int scoreL, int scoreT, int totalScore, String investmentType, String imageUrl){
        return TendencyResDTO.TendencyResultDTO.builder()
                .scoreR(scoreR)
                .scoreL(scoreL)
                .scoreT(scoreT)
                .totalScore(totalScore)
                .investmentType(investmentType)
                .imageBasicUrl(imageUrl)
                .build();
    }

    public static TendencyResDTO.TendencyResultDTO toTendencyResultDTO(Tendency tendency) {
        return TendencyResDTO.TendencyResultDTO.builder()
                .scoreR(tendency.getScoreR())
                .scoreL(tendency.getScoreL())
                .scoreT(tendency.getScoreT())
                .totalScore(tendency.getTotalScore())
                .investmentType(tendency.getInvestmentType())
                .imageBasicUrl(tendency.getImageUrl())
                .riskLabel(tendency.getRiskLabel())
                .periodLabel(tendency.getPeriodLabel())
                .familiarityLabel(tendency.getFamiliarityLabel())
                .build();
    }
}
