package com.example.givy.global.oauth.dto.req;

import lombok.Builder;
import lombok.Getter;

public class OauthReqDTO {

    @Getter
    @Builder
    public static class KakaoTokenRequest{
        //(실제로 요청 바디로 쓰지 않고, 파라미터로만 전달하지만 유지차원에서 만들어 두는 게 깔끔)
        private String grantType;
        private String clientId;
        private String code;
        private String redirectUri;
    }
}
