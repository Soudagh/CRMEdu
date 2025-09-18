package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Lesson;

public interface LessonRepository extends BaseCrudRepository<Lesson> {

  List<Lesson> getLessonsByTutorId(Long tutorId);

  List<Lesson> getLessonsByProgramId(Long programId);
}
