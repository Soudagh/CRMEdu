package org.example.crmedu.infrastructure.repository.lesson;

import java.util.List;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.repository.LessonRepository;
import org.example.crmedu.infrastructure.entity.LessonEntity;
import org.example.crmedu.infrastructure.mapping.LessonEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class LessonRepositoryImpl extends BaseRepository<Lesson, LessonEntity, Long> implements LessonRepository {

  private final DataLessonRepository lessonRepository;

  private final LessonEntityMapper mapper;

  public LessonRepositoryImpl(DataLessonRepository dataLessonRepository,
      LessonEntityMapper lessonEntityMapper) {
    super(dataLessonRepository, lessonEntityMapper);
    this.lessonRepository = dataLessonRepository;
    this.mapper = lessonEntityMapper;
  }

  @Override
  public List<Lesson> getLessonsByTutorId(Long tutorId) {
    var lessons = lessonRepository.findAllByTutor_Id(tutorId);
    return mapper.toDomain(lessons);
  }

  @Override
  public List<Lesson> getLessonsByProgramId(Long programId) {
    var lessons = lessonRepository.findAllBySubjectProgram_Program_Id(programId);
    return mapper.toDomain(lessons);
  }
}
