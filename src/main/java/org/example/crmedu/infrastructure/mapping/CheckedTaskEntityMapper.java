package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.CheckedTask;
import org.example.crmedu.infrastructure.entity.CheckedTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TutorEntityMapper.class, CompletedTaskEntityMapper.class})
public interface CheckedTaskEntityMapper extends BaseEntityMapper<CheckedTask, CheckedTaskEntity> {

  @Override
  @Mapping(source = "completedTaskEntity", target = "completedTask")
  @Mapping(source = "tutorEntity", target = "tutor")
  CheckedTask toDomain(CheckedTaskEntity entity);

  @Override
  @Mapping(source = "completedTask", target = "completedTaskEntity")
  @Mapping(source = "tutor", target = "tutorEntity")
  CheckedTaskEntity toEntity(CheckedTask domain);

  List<CheckedTask> toDomain(List<CheckedTaskEntity> entities);
}