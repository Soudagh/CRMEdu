package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Homework;

public interface HomeworkRepository extends BaseCrudRepository<Homework> {

  List<Homework> findByLessonId(Long lessonId);

  List<Homework> findPublishedByLessonIds(List<Long> lessonIds);

  List<Homework> findByTutorId(Long tutorId);
}