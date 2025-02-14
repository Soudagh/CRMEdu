package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.User;
import org.example.crmedu.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between User domain model and User JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface UserEntityMapper {

  /**
   * Converts a {@link UserEntity} to {@link User}.
   *
   * @param userEntity the user entity to convert
   * @return the {@link User} domain model
   */
  User userEntityToUser(UserEntity userEntity);

  /**
   * Converts a {@link User} to {@link UserEntity}.
   *
   * @param user the user domain model to convert
   * @return the {@link UserEntity} JPA-entity
   */
  UserEntity userToUserEntity(User user);
}
