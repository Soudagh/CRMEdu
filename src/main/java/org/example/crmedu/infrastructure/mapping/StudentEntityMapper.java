package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Student;
import org.example.crmedu.infrastructure.entity.StudentEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Student domain model and Student JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface StudentEntityMapper {

  /**
   * Converts a {@link StudentEntity} to {@link Student}.
   *
   * @param studentEntity the student entity to convert
   * @return the {@link Student} domain model
   */
  Student studentEntityToStudent(StudentEntity studentEntity);

  /**
   * Converts a {@link Student} to {@link StudentEntity}.
   *
   * @param student the student domain model to convert
   * @return the {@link StudentEntity} JPA-entity
   */
  StudentEntity studentToStudentEntity(Student student);
}
