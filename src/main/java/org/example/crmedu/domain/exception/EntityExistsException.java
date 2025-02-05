package org.example.crmedu.domain.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EntityExistsException extends RuntimeException {

  private Class<?> entityClass;

  private String field;

  private static final String ENTITY_EXISTS_MESSAGE = "%s with this %s already exists";

  public EntityExistsException(Class<?> entityClass, String field) {
    super(ENTITY_EXISTS_MESSAGE.formatted(entityClass.getSimpleName(), field));
    this.entityClass = entityClass;
    this.field = field;
  }
}
