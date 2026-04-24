package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Task;

public interface TaskRepository extends BaseCrudRepository<Task> {

  List<Task> findByHomeworkId(Long homeworkId);
}