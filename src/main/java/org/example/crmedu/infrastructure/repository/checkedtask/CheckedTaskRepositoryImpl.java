package org.example.crmedu.infrastructure.repository.checkedtask;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.domain.model.CheckedTask;
import org.example.crmedu.domain.repository.CheckedTaskRepository;
import org.example.crmedu.infrastructure.entity.CheckedTaskEntity;
import org.example.crmedu.infrastructure.mapping.CheckedTaskEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class CheckedTaskRepositoryImpl extends BaseRepository<CheckedTask, CheckedTaskEntity, Long>
    implements CheckedTaskRepository {

  private final DataCheckedTaskRepository checkedTaskRepository;
  private final CheckedTaskEntityMapper mapper;

  public CheckedTaskRepositoryImpl(DataCheckedTaskRepository checkedTaskRepository,
      CheckedTaskEntityMapper mapper) {
    super(checkedTaskRepository, mapper);
    this.checkedTaskRepository = checkedTaskRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<CheckedTask> findByCompletedTaskId(Long completedTaskId) {
    return checkedTaskRepository.findByCompletedTaskEntity_Id(completedTaskId)
        .map(mapper::toDomain);
  }

  @Override
  public List<CheckedTask> findUncheckedByTutorId(Long tutorId) {
    return mapper.toDomain(checkedTaskRepository.findAllByTutorEntity_IdAndIsCheckedFalse(tutorId));
  }
}