package org.example.crmedu.infrastructure.repository.completedtask;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.domain.model.CompletedTask;
import org.example.crmedu.domain.repository.CompletedTaskRepository;
import org.example.crmedu.infrastructure.entity.CompletedTaskEntity;
import org.example.crmedu.infrastructure.mapping.CompletedTaskEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class CompletedTaskRepositoryImpl extends BaseRepository<CompletedTask, CompletedTaskEntity, Long>
    implements CompletedTaskRepository {

  private final DataCompletedTaskRepository completedTaskRepository;
  private final CompletedTaskEntityMapper mapper;

  public CompletedTaskRepositoryImpl(DataCompletedTaskRepository completedTaskRepository,
      CompletedTaskEntityMapper mapper) {
    super(completedTaskRepository, mapper);
    this.completedTaskRepository = completedTaskRepository;
    this.mapper = mapper;
  }

  @Override
  public List<CompletedTask> findByHomeworkId(Long homeworkId) {
    return mapper.toDomain(completedTaskRepository.findAllByTaskEntity_HomeworkEntity_Id(homeworkId));
  }

  @Override
  public List<CompletedTask> findByStudentId(Long studentId) {
    return mapper.toDomain(completedTaskRepository.findAllByStudentEntity_Id(studentId));
  }

  @Override
  public Optional<CompletedTask> findByTaskIdAndStudentId(Long taskId, Long studentId) {
    return completedTaskRepository.findByTaskEntity_IdAndStudentEntity_Id(taskId, studentId)
        .map(mapper::toDomain);
  }
}