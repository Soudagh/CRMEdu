package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Student;
import org.example.crmedu.infrastructure.entity.StudentEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Student domain model and Student JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", uses = SubscriptionEntityMapper.class)
public interface StudentEntityMapper extends BaseEntityMapper<Student, StudentEntity> { }
