package org.example.crmedu.domain.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Exception thrown when an entity with a specific id is not found. Extends {@link RuntimeException}.
 */
@Setter
@Getter
@Accessors(chain = true)
public class EntityNotFoundException extends RuntimeException {

  /**
   * The class of the entity that caused the exception.
   */
  private Class<?> entityClass;

  /**
   * The id of non-existing entity.
   */
  private Object entityId;

  /**
   * The message template used for the exception.
   */
  private static final String ENTITY_NOT_FOUND_MESSAGE = "%s with id %s not found";

  /**
   * Constructs a new {@code EntityNotFound} with a formatted message.
   *
   * @param entityClass the class of the entity that caused the exception
   * @param entityId the id of the non-existing entity
   */
  public EntityNotFoundException(Class<?> entityClass, Object entityId) {
    super(ENTITY_NOT_FOUND_MESSAGE.formatted(entityClass.getSimpleName(), entityId));
    this.entityClass = entityClass;
    this.entityId = entityId;
  }
}
