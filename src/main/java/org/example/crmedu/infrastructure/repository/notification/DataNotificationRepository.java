package org.example.crmedu.infrastructure.repository.notification;

import java.util.List;
import org.example.crmedu.infrastructure.entity.NotificationEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataNotificationRepository extends BaseDataRepository<NotificationEntity, Long> {

  List<NotificationEntity> findAllByUser_IdOrderByCreatedAtDesc(Long userId);

  List<NotificationEntity> findAllByUser_IdAndIsReadFalse(Long userId);
}