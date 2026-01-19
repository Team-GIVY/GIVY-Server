package com.example.givy.domain.user.dto.req;

import lombok.Builder;
import lombok.Getter;

public class UserNotificationReqDTO {
    @Builder
    @Getter
    public static class UserNotificationListParamDTO {
        @Builder.Default
        private int page = 0;

        @Builder.Default
        private int size = 20;

        private Boolean isRead;
    }
}
