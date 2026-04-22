package org.example.crmedu.infrastructure.repository.homework;

import java.util.List;
import org.example.crmedu.domain.enums.HomeworkStatus;
import org.example.crmedu.domain.model.Homework;
import org.example.crmedu.domain.repository.HomeworkRepository;
import org.example.crmedu.infrastructure.entity.HomeworkEntity;
import org.example.crmedu.infrastructure.mapping.HomeworkEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class HomeworkRepositoryImpl extends BaseRepository<Homework, HomeworkEntity, Long> implements HomeworkRepository {

  private final DataHomeworkRepository homeworkRepository;
  private final HomeworkEntityMapper mapper;

  public HomeworkRepositoryImpl(DataHomeworkRepository homeworkRepository, HomeworkEntityMapper mapper) {
    super(homeworkRepository, mapper);
    this.homeworkRepository = homeworkRepository;
    this.mapper = mapper;
  }

  @Override
  public List<Homework> findByLessonId(Long lessonId) {
    return mapper.toDomain(homeworkRepository.findAllByLessonEntity_Id(lessonId));
  }

  @Override
  public List<Homework> findPublishedByLessonIds(List<Long> lessonIds) {
    return mapper.toDomain(
        homeworkRepository.findAllByStatusAndLessonEntity_IdIn(HomeworkStatus.PUBLISHED, lessonIds)
    );
  }

  @Override
  public List<Homework> findByTutorId(Long tutorId) {
    return mapper.toDomain(homeworkRepository.findAllByTutorId(tutorId));
  }
}