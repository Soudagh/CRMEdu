package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.User;
import org.example.crmedu.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between User domain model and User JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", uses = NotificationEntityMapper.class)
public interface UserEntityMapper extends BaseEntityMapper<User, UserEntity> { }
