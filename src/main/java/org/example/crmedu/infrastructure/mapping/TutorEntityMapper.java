package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.infrastructure.entity.TutorEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Tutor domain model and Tutor JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface TutorEntityMapper {

  /**
   * Converts a {@link Tutor} to {@link TutorEntity}.
   *
   * @param tutor the tutor domain model to convert
   * @return the {@link TutorEntity} JPA-entity
   */
  TutorEntity toTutorEntity(Tutor tutor);

  /**
   * Converts a {@link TutorEntity} to {@link Tutor}.
   *
   * @param tutorEntity the tutor entity to convert
   * @return the {@link Tutor} domain model
   */
  Tutor toTutor(TutorEntity tutorEntity);
}
