package org.example.crmedu.infrastructure.repository.student;

import java.util.List;
import org.example.crmedu.domain.enums.SubscriptionStatus;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.repository.StudentRepository;
import org.example.crmedu.infrastructure.entity.LessonEntity;
import org.example.crmedu.infrastructure.entity.StudentEntity;
import org.example.crmedu.infrastructure.mapping.StudentEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link StudentRepository } interface. Provides methods for managing {@link Student } entities in the database.
 */
@Component
public class StudentRepositoryImpl extends BaseRepository<Student, StudentEntity, Long> implements StudentRepository {

  private final StudentEntityMapper mapper;

  private final DataStudentRepository studentRepository;

  public StudentRepositoryImpl(
      DataStudentRepository studentRepository,
      StudentEntityMapper mapper
  ) {
    super(studentRepository, mapper);
    this.studentRepository = studentRepository;
    this.mapper = mapper;
  }

  @Override
  public Student getStudentByUserId(Long userId) {
    return mapper.toDomain(studentRepository.getStudentEntityByUser_Id(userId));
  }

  @Override
  public List<Student> findByProgramId(Long programId) {
    return studentRepository
        .findAllBySubscriptions_Program_IdAndSubscriptions_SubscriptionStatus(programId, SubscriptionStatus.ACTIVE)
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

  private Lesson mapToDomain(LessonEntity entity) {
    return new Lesson()
        .setId(entity.getId())
        .setLessonStatus(entity.getLessonStatus())
        .setNotes(entity.getNotes())
        .setStartTime(entity.getStartTime())
        .setEndTime(entity.getEndTime());
  }
}


