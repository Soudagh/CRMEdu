package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Homework;
import org.example.crmedu.infrastructure.entity.HomeworkEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LessonEntityMapper.class)
public interface HomeworkEntityMapper extends BaseEntityMapper<Homework, HomeworkEntity> {

  @Override
  @Mapping(source = "lessonEntity", target = "lesson")
  Homework toDomain(HomeworkEntity entity);

  @Override
  @Mapping(source = "lesson", target = "lessonEntity")
  HomeworkEntity toEntity(Homework domain);

  List<Homework> toDomain(List<HomeworkEntity> entities);
}