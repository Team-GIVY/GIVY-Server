package com.example.givy.domain.user.converter;

import com.example.givy.domain.user.dto.res.UserNotificationResDTO;
import com.example.givy.domain.user.entity.UserNotification;
import org.springframework.data.domain.Page;

public class UserNotificationConverter {
    //entity -> dto
    public static UserNotificationResDTO.UserNotificationInfoDTO toUserNotificationInfoDTO(UserNotification userNotification) {
        return UserNotificationResDTO.UserNotificationInfoDTO.builder()
                .userNotificationId(userNotification.getUserNotificationId())
                .notificationId(userNotification.getNotification().getNotificationId())
                .notificationType(userNotification.getNotification().getNotificationType())
                .title(userNotification.getNotification().getTitle())
                .body(userNotification.getNotification().getBody())
                .targetType(userNotification.getNotification().getTargetType())
                .targetId(String.valueOf(userNotification.getNotification().getTargetId()))
                .isRead(userNotification.getIsRead())
                .deliveredAt(userNotification.getDeliveredAt())
                .createdAt(userNotification.getCreatedAt())
                .build();
    }

    //page<entity> -> dto
    public static UserNotificationResDTO.UserNotificationInfoListDTO toUserNotificationInfoListDTO(Page<UserNotification> userNotification){
        return UserNotificationResDTO.UserNotificationInfoListDTO.builder()
                .userNotificationInfo(userNotification.getContent().stream()
                        .map(UserNotificationConverter::toUserNotificationInfoDTO)
                        .toList())
                .isFirst(userNotification.isFirst())
                .isLast(userNotification.isLast())
                .listSize(userNotification.getSize())
                .totalElements(userNotification.getTotalElements())
                .totalPage(userNotification.getTotalPages())
                .build();
    }

    //entity -> dto
    public static UserNotificationResDTO.ReadUserNotification toUserNotificationSimplyInfoDTO(UserNotification notification){
        return UserNotificationResDTO.ReadUserNotification.builder()
                .userNotificationId(notification.getUserNotificationId())
                .isRead(notification.getIsRead())
                .build();
    }

}
