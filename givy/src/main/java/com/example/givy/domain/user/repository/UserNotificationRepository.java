package com.example.givy.domain.user.repository;

import com.example.givy.domain.notification.enums.NotificationType;
import com.example.givy.domain.user.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    boolean existsByUsers_UserIdAndNotification_NotificationType(
            Long userId,
            NotificationType notificationType
    );
}
