package com.example.givy.domain.kis;

import com.example.givy.domain.kis.service.KisMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class KisMarketTestRunner implements CommandLineRunner {

    private final KisMarketService kisMarketService;

    @Override
    public void run(String... args) {
        System.out.println("\n========== KIS API 휴장일 조회 테스트 ==========");
        LocalDate today = LocalDate.now();
        boolean isOpen = kisMarketService.isMarketOpen(today);
        System.out.println("날짜: " + today);
        System.out.println("오늘은 장이 열리는 날인가요? " + (isOpen ? "네 (개장일)" : "아니요 (휴장일)"));
        System.out.println("==============================================\n");
    }
}
