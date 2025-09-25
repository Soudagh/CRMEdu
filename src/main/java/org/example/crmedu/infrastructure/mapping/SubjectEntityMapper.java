package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.infrastructure.entity.SubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper interface for converting between Subject domain model and Subject JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface SubjectEntityMapper extends BaseEntityMapper<Subject, SubjectEntity> {

  @Mapping(target = "organization", ignore = true)
  Subject toDomain(SubjectEntity subject);
}
