package org.example.crmedu.infrastructure.repository.completedtask;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.infrastructure.entity.CompletedTaskEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCompletedTaskRepository extends BaseDataRepository<CompletedTaskEntity, Long> {

  List<CompletedTaskEntity> findAllByTaskEntity_HomeworkEntity_Id(Long homeworkId);

  List<CompletedTaskEntity> findAllByStudentEntity_Id(Long studentId);

  Optional<CompletedTaskEntity> findByTaskEntity_IdAndStudentEntity_Id(Long taskId, Long studentId);
}