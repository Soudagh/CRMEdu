package org.example.crmedu.application.mapping;

import java.util.List;
import org.example.crmedu.application.dto.response.notification.GetNotificationResponse;
import org.example.crmedu.domain.model.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationDTOMapper {

  GetNotificationResponse notificationToGetResponse(Notification notification);

  List<GetNotificationResponse> notificationsToGetResponses(List<Notification> notifications);
}