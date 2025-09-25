package org.example.crmedu.domain.service.notification;

import java.util.List;
import org.example.crmedu.domain.model.Notification;
import org.example.crmedu.domain.model.User;

public class NotificationServiceImpl implements NotificationService {

  @Override
  public List<Notification> getUserNotifications(User user) {
    return List.of();
  }
}
