package com.example.givy.domain.recommendation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InvestmentType {
    PARKING("파킹형 ETF"),
    FUND("펀드형"),
    ASSET("자산분배형 ETF"),
    MARKET("시장지수형 ETF"),
    THEME("테마투자형");

    private final String value;
}
