package org.example.crmedu.infrastructure.repository.checkedtask;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.infrastructure.entity.CheckedTaskEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCheckedTaskRepository extends BaseDataRepository<CheckedTaskEntity, Long> {

  Optional<CheckedTaskEntity> findByCompletedTaskEntity_Id(Long completedTaskId);

  List<CheckedTaskEntity> findAllByTutorEntity_IdAndIsCheckedFalse(Long tutorId);
}