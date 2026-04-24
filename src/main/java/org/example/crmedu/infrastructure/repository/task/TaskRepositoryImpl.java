package org.example.crmedu.infrastructure.repository.task;

import java.util.List;
import org.example.crmedu.domain.model.Task;
import org.example.crmedu.domain.repository.TaskRepository;
import org.example.crmedu.infrastructure.entity.TaskEntity;
import org.example.crmedu.infrastructure.mapping.TaskEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskRepositoryImpl extends BaseRepository<Task, TaskEntity, Long> implements TaskRepository {

  private final DataTaskRepository taskRepository;
  private final TaskEntityMapper mapper;

  public TaskRepositoryImpl(DataTaskRepository taskRepository, TaskEntityMapper mapper) {
    super(taskRepository, mapper);
    this.taskRepository = taskRepository;
    this.mapper = mapper;
  }

  @Override
  public List<Task> findByHomeworkId(Long homeworkId) {
    return mapper.toDomain(taskRepository.findAllByHomeworkEntity_Id(homeworkId));
  }
}