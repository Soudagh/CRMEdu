package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Task;
import org.example.crmedu.infrastructure.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = HomeworkEntityMapper.class)
public interface TaskEntityMapper extends BaseEntityMapper<Task, TaskEntity> {

  @Override
  @Mapping(source = "homeworkEntity", target = "homework")
  Task toDomain(TaskEntity entity);

  @Override
  @Mapping(source = "homework", target = "homeworkEntity")
  TaskEntity toEntity(Task domain);

  List<Task> toDomain(List<TaskEntity> entities);
}