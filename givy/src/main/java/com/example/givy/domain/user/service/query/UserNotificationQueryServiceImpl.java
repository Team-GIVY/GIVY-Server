package com.example.givy.domain.user.service.query;

import com.example.givy.domain.user.converter.UserNotificationConverter;
import com.example.givy.domain.user.dto.req.UserNotificationReqDTO;
import com.example.givy.domain.user.dto.res.UserNotificationResDTO;
import com.example.givy.domain.user.entity.UserNotification;
import com.example.givy.domain.user.repository.UserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserNotificationQueryServiceImpl implements UserNotificationQueryService{
    private final UserNotificationRepository userNotificationRepository;

    @Override
    public UserNotificationResDTO.UserNotificationInfoListDTO getUserNotifications(Long userId, UserNotificationReqDTO.UserNotificationListParamDTO dto) {
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize());

        Page<UserNotification> notifications = userNotificationRepository.findAllByUserIdAndOptionalIsRead(userId, dto.getIsRead(), pageRequest);

        return UserNotificationConverter.toUserNotificationInfoListDTO(notifications);
    }
}
