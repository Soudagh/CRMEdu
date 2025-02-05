package org.example.crmedu.domain.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class EntityNotFoundException extends RuntimeException {

  private Class<?> entityClass;

  private Object entityId;

  private static final String ENTITY_NOT_FOUND_MESSAGE = "%s with id %s not found";

  public EntityNotFoundException(Class<?> entityClass, Object entityId) {
    super(ENTITY_NOT_FOUND_MESSAGE.formatted(entityClass.getSimpleName(), entityId));
    this.entityClass = entityClass;
    this.entityId = entityId;
  }
}
