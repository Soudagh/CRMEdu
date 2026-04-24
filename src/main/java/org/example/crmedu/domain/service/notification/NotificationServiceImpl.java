package org.example.crmedu.domain.service.notification;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.exception.ResourceAccessDeniedException;
import org.example.crmedu.domain.model.Notification;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  @Override
  @Transactional
  public List<Notification> getUserNotifications(User user) {
    return notificationRepository.findByUserId(user.getId());
  }

  @Override
  @Transactional
  public Notification create(Notification notification) {
    return notificationRepository.create(notification);
  }

  @Override
  @Transactional
  public void markAsRead(Long notificationId, Long userId) {
    var notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new EntityNotFoundException(Notification.class, notificationId));
    if (!notification.getUser().getId().equals(userId)) {
      throw new ResourceAccessDeniedException("Cannot mark another user's notification as read");
    }
    notificationRepository.markAsRead(notificationId);
  }

  @Override
  @Transactional
  public void markAllAsRead(Long userId) {
    notificationRepository.markAllAsReadByUserId(userId);
  }
}