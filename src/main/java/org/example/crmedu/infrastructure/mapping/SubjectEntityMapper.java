package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.infrastructure.entity.SubjectEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Subject domain model and Subject JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface SubjectEntityMapper {

  /**
   * Converts a {@link SubjectEntity} to {@link Subject}.
   *
   * @param subjectEntity the subject entity to convert
   * @return the {@link Subject} domain model
   */
  Subject toSubject(SubjectEntity subjectEntity);

  /**
   * Converts a {@link Subject} to {@link SubjectEntity}.
   *
   * @param subject the subject domain model to convert
   * @return the {@link SubjectEntity} JPA-entity
   */
  SubjectEntity toSubjectEntity(Subject subject);
}
