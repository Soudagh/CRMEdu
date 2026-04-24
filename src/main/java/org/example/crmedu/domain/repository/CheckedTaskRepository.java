package org.example.crmedu.domain.repository;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.domain.model.CheckedTask;

public interface CheckedTaskRepository extends BaseCrudRepository<CheckedTask> {

  Optional<CheckedTask> findByCompletedTaskId(Long completedTaskId);

  List<CheckedTask> findUncheckedByTutorId(Long tutorId);
}