package org.example.crmedu.domain.exception;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UniqueConstraintsException extends RuntimeException {

  private Class<?> entityClass;

  private List<UniqueConstraintError> fields;

  private static final String UNIQUE_CONSTRAINT_MESSAGE = "%s with %s %s already exists";

  public UniqueConstraintsException(Class<?> entityClass, List<UniqueConstraintError> fields) {

    super(fields.stream().map(error ->
        UNIQUE_CONSTRAINT_MESSAGE.formatted(entityClass.getSimpleName(), error.getFieldName(), error.getFieldValue())
    ).toList().toString());
    this.entityClass = entityClass;
    this.fields = fields;
  }
}
