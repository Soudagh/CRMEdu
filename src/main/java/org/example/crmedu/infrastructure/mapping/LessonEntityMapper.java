package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.infrastructure.entity.LessonEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonEntityMapper extends BaseEntityMapper<Lesson, LessonEntity> {

  Lesson toDomain(LessonEntity lesson);

  List<Lesson> toDomain(List<LessonEntity> lessons);

  LessonEntity idToEntity(Long id);

  default Long entityToId(LessonEntity lesson) {
    return lesson != null ? lesson.getId() : null;
  }
}
