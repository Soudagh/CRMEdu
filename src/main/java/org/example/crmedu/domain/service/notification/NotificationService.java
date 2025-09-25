package org.example.crmedu.domain.service.notification;

import java.util.List;
import org.example.crmedu.domain.model.Notification;
import org.example.crmedu.domain.model.User;

public interface NotificationService {
  List<Notification> getUserNotifications(User user);
}
