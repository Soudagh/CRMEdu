package org.example.crmedu.infrastructure.repository.homework;

import java.util.List;
import org.example.crmedu.domain.enums.HomeworkStatus;
import org.example.crmedu.infrastructure.entity.HomeworkEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataHomeworkRepository extends BaseDataRepository<HomeworkEntity, Long> {

  List<HomeworkEntity> findAllByLessonEntity_Id(Long lessonId);

  List<HomeworkEntity> findAllByStatusAndLessonEntity_IdIn(HomeworkStatus status, List<Long> lessonIds);

  @Query("SELECT h FROM HomeworkEntity h WHERE h.lessonEntity.tutor.id = :tutorId ORDER BY h.startDate DESC")
  List<HomeworkEntity> findAllByTutorId(@Param("tutorId") Long tutorId);
}