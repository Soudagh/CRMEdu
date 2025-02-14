package org.example.crmedu.domain.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Exception thrown when an entity with a unique field constraint already exists. Extends {@link RuntimeException}.
 */
@Getter
@Setter
@Accessors(chain = true)
public class EntityExistsException extends RuntimeException {

  /**
   * The class of the entity that caused the exception.
   */
  private Class<?> entityClass;

  /**
   * The field that triggered the uniqueness constraint violation.
   */
  private String field;

  /**
   * The message template used for the exception.
   */
  private static final String ENTITY_EXISTS_MESSAGE = "%s with this %s already exists";

  /**
   * Constructs a new {@code EntityExistsException} with a formatted message.
   *
   * @param entityClass the class of the entity that caused the exception
   * @param field the name of the field that violates the uniqueness constraint
   */
  public EntityExistsException(Class<?> entityClass, String field) {
    super(ENTITY_EXISTS_MESSAGE.formatted(entityClass.getSimpleName(), field));
    this.entityClass = entityClass;
    this.field = field;
  }
}
