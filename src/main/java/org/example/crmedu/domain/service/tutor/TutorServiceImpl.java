package org.example.crmedu.domain.service.tutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.example.crmedu.domain.exception.UniqueConstraintsException;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.exception.UniqueConstraintError;
import org.example.crmedu.domain.repository.TutorRepository;
import org.example.crmedu.domain.service.BaseService;
import org.example.crmedu.domain.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link TutorService} interface. Provides business logic for managing {@link Tutor} entities.
 */
@Service
public class TutorServiceImpl extends BaseService<Tutor> implements TutorService {

  private final TutorRepository tutorRepository;

  private final LessonRepository lessonRepository;

  public TutorServiceImpl(TutorRepository tutorRepository, LessonRepository lessonRepository) {
    super(tutorRepository, Tutor.class);
    this.tutorRepository = tutorRepository;
    this.lessonRepository = lessonRepository;
  }

  @Override
  public Tutor create(Tutor tutor) {
    checkTutorBelongsToUser(tutor);
    return tutorRepository.create(tutor);
  }

  @Override
  @Transactional
  public void update(Tutor tutor, Long id) {
    var tutorEntity = findById(id);
    var user = tutorEntity.getUser();
    tutorRepository.update(tutor.setUser(user).setId(id));
  }

  @Override
  @Transactional
  public void patchSubjects(Set<Subject> subjects, Long id) {
    var tutorEntity = findById(id);
    tutorRepository.update(tutorEntity.setSubjects(subjects));
  }

  @Override
  @Transactional
  public void patchGrades(Set<Integer> grades, Long id) {
    var tutorEntity = findById(id);
    tutorRepository.update(tutorEntity.setGrades(grades));
  }

  @Override
  public List<Lesson> getScheduleByUserId(Long userId) {
    var tutor = tutorRepository.findTutorByUserId(userId);
    return lessonRepository.getLessonsByTutorId(tutor.getId());
  }

  private void checkTutorBelongsToUser(Tutor tutor) {
    var violations = new ArrayList<UniqueConstraintError>();
    if (tutorRepository.existsByUser(tutor)) {
      violations.add(new UniqueConstraintError("user", tutor.getUser().getId().toString()));
    }
    if (!violations.isEmpty()) {
      throw new UniqueConstraintsException(Tutor.class, violations);
    }
  }
}
