package org.example.crmedu.domain.service.student;

import java.util.List;
import org.example.crmedu.domain.enums.SubscriptionStatus;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.model.Subscription;
import org.example.crmedu.domain.repository.LessonRepository;
import org.example.crmedu.domain.repository.StudentRepository;
import org.example.crmedu.domain.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link StudentService} interface. Provides business logic for managing {@link Student} entities.
 */
@Service
public class StudentServiceImpl extends BaseService<Student> implements StudentService {

  private final StudentRepository studentRepository;

  private final LessonRepository lessonRepository;


  public StudentServiceImpl(StudentRepository studentRepository, LessonRepository lessonRepository) {
    super(studentRepository, Student.class);
    this.studentRepository = studentRepository;
    this.lessonRepository = lessonRepository;
  }


  @Override
  public Student create(Student student) {
    return studentRepository.create(student);
  }

  @Override
  @Transactional
  public void update(Student student, Long id) {
    var studentEntity = findById(id);
    student.setUser(studentEntity.getUser());
    studentRepository.update(
        student.setId(id)
    );
  }

  @Override
  @Transactional
  public List<Lesson> getScheduleByUserId(Long userId) {
    var student = getStudentByUserId(userId);
    return student.getSubscriptions().stream()
        .filter(subscription -> subscription.getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE))
        .map(Subscription::getProgram)
        .flatMap(program -> lessonRepository.getLessonsByProgramId(program.getId()).stream())
        .toList();
  }

  @Override
  @Transactional
  public Student getStudentByUserId(Long userId) {
    return studentRepository.getStudentByUserId(userId);
  }
}

