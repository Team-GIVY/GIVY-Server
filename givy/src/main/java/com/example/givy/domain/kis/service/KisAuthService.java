package com.example.givy.domain.kis.service;

import com.example.givy.domain.kis.dto.KisTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KisAuthService {

    @Value("${kis.api.app-key}") private String appKey;
    @Value("${kis.api.app-secret}") private String appSecret;
    @Value("${kis.api.url}") private String baseUrl;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private String accessToken; // 메모리 캐싱
    private LocalDateTime tokenExpirationTime; // 토큰 만료 시간

    private static final String TOKEN_FILE_PATH = "kis_token.json"; // 토큰 저장 파일명

    // 서버 시작 시 및 6시간마다 토큰 갱신
    @Scheduled(fixedRate = 1000 * 60 * 60 * 6)
    public void refreshAccessToken() {
        // 1. 파일에서 토큰 불러오기 시도 (유효하면 API 호출 스킵)
        if (loadTokenFromFile()) {
            return;
        }

        log.info("KIS 액세스 토큰 갱신 시도 중...");

        String cleanKey = appKey.trim().replace("\"", "");
        String cleanSecret = appSecret.trim().replace("\"", "");

        if (cleanKey.startsWith("${") || cleanSecret.startsWith("${")) {
            log.error("KIS API Key가 설정되지 않았습니다.");
            return;
        }

        String requestUrl = baseUrl;
        if (requestUrl.endsWith("/oauth2/tokenP")) {
        } else {
            if (requestUrl.endsWith("/")) {
                requestUrl = requestUrl.substring(0, requestUrl.length() - 1);
            }
            requestUrl += "/oauth2/tokenP";
        }

        KisTokenResponse response = webClient.post()
                .uri(requestUrl)
                .header("Content-Type", "application/json")
                .bodyValue(Map.of(
                        "grant_type", "client_credentials",
                        "appkey", cleanKey,
                        "appsecret", cleanSecret
                ))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class).doOnNext(body ->
                                log.error(" KIS 에러 상세 내용: {}", body)
                        ).then(Mono.error(new RuntimeException("KIS API 호출 실패")))
                )
                .bodyToMono(KisTokenResponse.class)
                .block();

        if (response != null && response.getAccessToken() != null) {
            this.accessToken = response.getAccessToken();
            // 만료 시간 설정 (여유 있게 10분 차감)
            this.tokenExpirationTime = LocalDateTime.now().plusSeconds(response.getExpiresIn() - 600);
            log.info("KIS 토큰 갱신 완료 (유효기간: {}초)", response.getExpiresIn());
            
            // 파일에 저장
            saveTokenToFile();
        } else {
            log.error("KIS 토큰 발급 실패함");
        }
    }

    public String getStoredToken() {
        if (accessToken == null || (tokenExpirationTime != null && LocalDateTime.now().isAfter(tokenExpirationTime))) {
            refreshAccessToken();
        }
        return accessToken;
    }

    // --- 파일 캐싱 로직 ---

    private void saveTokenToFile() {
        try {
            TokenData tokenData = new TokenData(this.accessToken, this.tokenExpirationTime.toString());
            File file = new File(TOKEN_FILE_PATH);
            objectMapper.writeValue(file, tokenData);
            log.info("KIS 토큰 파일 저장 완료: {}", file.getAbsolutePath());
        } catch (IOException e) {
            log.warn("토큰 파일 저장 실패: {}", e.getMessage());
        }
    }

    private boolean loadTokenFromFile() {
        try {
            File file = new File(TOKEN_FILE_PATH);
            if (!file.exists()) return false;

            TokenData tokenData = objectMapper.readValue(file, TokenData.class);
            LocalDateTime expiration = LocalDateTime.parse(tokenData.expirationTime);

            // 토큰이 유효한지 확인 (현재 시간보다 미래인지)
            if (expiration.isAfter(LocalDateTime.now())) {
                this.accessToken = tokenData.accessToken;
                this.tokenExpirationTime = expiration;
                log.info("파일에서 유효한 토큰 로드 완료 (만료: {})", expiration);
                return true; // 로드 성공
            } else {
                log.info("파일에 저장된 토큰이 만료되었습니다.");
            }
        } catch (Exception e) {
            log.warn("토큰 파일 로드 실패 (새로 발급받습니다): {}", e.getMessage());
        }
        return false; // 로드 실패 또는 만료됨
    }

    // JSON 저장용 내부 클래스
    private record TokenData(String accessToken, String expirationTime) {}
}
