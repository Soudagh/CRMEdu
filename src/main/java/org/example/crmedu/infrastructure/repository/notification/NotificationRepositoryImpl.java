package org.example.crmedu.infrastructure.repository.notification;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Notification;
import org.example.crmedu.domain.repository.NotificationRepository;
import org.example.crmedu.infrastructure.mapping.NotificationEntityMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

  private final DataNotificationRepository notificationRepository;
  private final NotificationEntityMapper mapper;

  @Override
  public Notification create(Notification notification) {
    var entity = mapper.toEntity(notification);
    return mapper.toDomain(notificationRepository.save(entity));
  }

  @Override
  public Optional<Notification> findById(Long id) {
    return notificationRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public List<Notification> findByUserId(Long userId) {
    return mapper.toDomain(notificationRepository.findAllByUser_IdOrderByCreatedAtDesc(userId));
  }

  @Override
  public void markAsRead(Long notificationId) {
    notificationRepository.findById(notificationId).ifPresent(n -> {
      n.setIsRead(true);
      notificationRepository.save(n);
    });
  }

  @Override
  public void markAllAsReadByUserId(Long userId) {
    var notifications = notificationRepository.findAllByUser_IdAndIsReadFalse(userId);
    notifications.forEach(n -> n.setIsRead(true));
    notificationRepository.saveAll(notifications);
  }
}