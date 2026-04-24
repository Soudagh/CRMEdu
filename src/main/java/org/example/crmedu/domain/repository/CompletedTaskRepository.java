package org.example.crmedu.domain.repository;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.domain.model.CompletedTask;

public interface CompletedTaskRepository extends BaseCrudRepository<CompletedTask> {

  List<CompletedTask> findByHomeworkId(Long homeworkId);

  List<CompletedTask> findByStudentId(Long studentId);

  Optional<CompletedTask> findByTaskIdAndStudentId(Long taskId, Long studentId);
}