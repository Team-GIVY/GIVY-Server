package com.example.givy.domain.recommendation.dto.res;

import lombok.Builder;

public class TendencyResDTO {

    @Builder
    public record TendencyResultDTO(
            String investmentType,
            int scoreR,
            int scoreL,
            int scoreT,
            int totalScore,
            String imageBasicUrl,
            String riskLabel,        // 예: "안정추구"
            String periodLabel,      // 예: "단기"
            String familiarityLabel  // 예: "안정형"
    ){}
}
