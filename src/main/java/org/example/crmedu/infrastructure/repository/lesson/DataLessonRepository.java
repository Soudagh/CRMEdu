package org.example.crmedu.infrastructure.repository.lesson;

import java.util.List;
import org.example.crmedu.infrastructure.entity.LessonEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataLessonRepository extends BaseDataRepository<LessonEntity, Long> {

  List<LessonEntity> findAllByTutor_Id(Long tutorId);

  List<LessonEntity> findAllBySubjectProgram_Program_Id(Long programId);
}
