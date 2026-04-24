package org.example.crmedu.domain.repository;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.domain.model.Notification;

public interface NotificationRepository {

  Notification create(Notification notification);

  Optional<Notification> findById(Long id);

  List<Notification> findByUserId(Long userId);

  void markAsRead(Long notificationId);

  void markAllAsReadByUserId(Long userId);
}