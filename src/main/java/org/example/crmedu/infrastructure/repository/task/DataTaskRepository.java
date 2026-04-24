package org.example.crmedu.infrastructure.repository.task;

import java.util.List;
import org.example.crmedu.infrastructure.entity.TaskEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTaskRepository extends BaseDataRepository<TaskEntity, Long> {

  List<TaskEntity> findAllByHomeworkEntity_Id(Long homeworkId);
}