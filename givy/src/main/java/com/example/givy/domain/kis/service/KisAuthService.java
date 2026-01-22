    package com.example.givy.domain.kis.service;

    import com.example.givy.domain.kis.dto.KisTokenResponse;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.http.HttpStatusCode;
    import org.springframework.scheduling.annotation.Scheduled;
    import org.springframework.stereotype.Service;
    import org.springframework.web.reactive.function.client.WebClient;
    import reactor.core.publisher.Mono;

    import java.util.Map;

    @Service
    @Slf4j
    @RequiredArgsConstructor
    public class KisAuthService {

        @Value("${kis.api.app-key}") private String appKey;
        @Value("${kis.api.app-secret}") private String appSecret;
        @Value("${kis.api.url}") private String baseUrl;

        private final WebClient webClient;
        private String accessToken; // 메모리 캐싱

        // 서버 시작 시 및 6시간마다 토큰 갱신
        @Scheduled(fixedRate = 1000 * 60 * 60 * 6)
        public void refreshAccessToken() {
            log.info("KIS 액세스 토큰 갱신 시도 중...");

            String cleanKey = appKey.trim().replace("\"", "");
            String cleanSecret = appSecret.trim().replace("\"", "");

            if (cleanKey.startsWith("${") || cleanSecret.startsWith("${")) {
                log.error("❌ KIS API Key가 설정되지 않았습니다. application.yml 또는 환경 변수를 확인해주세요.");
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
                log.info("KIS 토큰 갱신 완료 유효시간: {}초", response.getExpiresIn());
            } else {
                log.error("KIS 토큰 발급 실패함");
            }
        }

        public String getStoredToken() {
            if (accessToken == null) {
                refreshAccessToken();
            }
            return accessToken;
        }
    }
