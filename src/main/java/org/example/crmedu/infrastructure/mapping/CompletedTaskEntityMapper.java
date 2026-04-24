package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.CompletedTask;
import org.example.crmedu.infrastructure.entity.CompletedTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TaskEntityMapper.class, StudentEntityMapper.class})
public interface CompletedTaskEntityMapper extends BaseEntityMapper<CompletedTask, CompletedTaskEntity> {

  @Override
  @Mapping(source = "taskEntity", target = "task")
  @Mapping(source = "studentEntity", target = "student")
  CompletedTask toDomain(CompletedTaskEntity entity);

  @Override
  @Mapping(source = "task", target = "taskEntity")
  @Mapping(source = "student", target = "studentEntity")
  CompletedTaskEntity toEntity(CompletedTask domain);

  List<CompletedTask> toDomain(List<CompletedTaskEntity> entities);
}