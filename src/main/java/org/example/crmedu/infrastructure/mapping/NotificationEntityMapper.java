package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Notification;
import org.example.crmedu.infrastructure.entity.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationEntityMapper extends BaseEntityMapper<Notification, NotificationEntity>{

}
