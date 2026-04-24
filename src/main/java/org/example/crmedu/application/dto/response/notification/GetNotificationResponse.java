package org.example.crmedu.application.dto.response.notification;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.NotificationType;

@Data
@Accessors(chain = true)
public class GetNotificationResponse {

  private Long id;

  private String title;

  private String description;

  private String link;

  private NotificationType notificationType;

  private ZonedDateTime createdAt;

  private Boolean isRead;
}