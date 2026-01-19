package com.example.givy.domain.user.service.query;

import com.example.givy.domain.user.dto.req.UserNotificationReqDTO;
import com.example.givy.domain.user.dto.res.UserNotificationResDTO;

public interface UserNotificationQueryService {
    UserNotificationResDTO.UserNotificationInfoListDTO getUserNotifications(Long userId, UserNotificationReqDTO.UserNotificationListParamDTO dto);
}
