package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.infrastructure.entity.TutorEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Tutor domain model and Tutor JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", uses = LessonEntityMapper.class)
public interface TutorEntityMapper extends BaseEntityMapper<Tutor, TutorEntity> {

  Tutor toDomain(TutorEntity entity);
}
