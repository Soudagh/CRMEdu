package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Notification;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.infrastructure.entity.NotificationEntity;
import org.example.crmedu.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NotificationEntityMapper extends BaseEntityMapper<Notification, NotificationEntity> {

  @Mapping(target = "user", source = "user", qualifiedByName = "userWithIdOnly")
  Notification toDomain(NotificationEntity entity);

  @Mapping(target = "user", source = "user", qualifiedByName = "userEntityWithIdOnly")
  NotificationEntity toEntity(Notification domain);

  List<Notification> toDomain(List<NotificationEntity> entities);

  @Named("userWithIdOnly")
  default User userWithIdOnly(UserEntity entity) {
    if (entity == null) {
      return null;
    }
    return new User().setId(entity.getId());
  }

  @Named("userEntityWithIdOnly")
  default UserEntity userEntityWithIdOnly(User domain) {
    if (domain == null) {
      return null;
    }
    return new UserEntity().setId(domain.getId());
  }
}
