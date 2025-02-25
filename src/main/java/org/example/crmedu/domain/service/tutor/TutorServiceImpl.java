package org.example.crmedu.domain.service.tutor;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link TutorService} interface. Provides business logic for managing {@link Tutor} entities.
 */
@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

  private final TutorRepository tutorRepository;

  @Override
  public Tutor create(Tutor tutor) {
    checkTutorBelongsToUser(tutor);
    return tutorRepository.save(tutor);
  }

  @Override
  @Transactional(readOnly = true)
  public Tutor findById(Long id) {
    return tutorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Tutor.class, id));
  }

  @Override
  public Page<Tutor> findAll(int pageNumber, int pageSize) {
    return tutorRepository.findAll(pageNumber, pageSize);
  }

  @Override
  @Transactional
  public void update(Tutor tutor, Long id) {
    var tutorEntity = findById(id);
    var user = tutorEntity.getUser();
    tutorRepository.update(tutor.setUser(user).setId(id));
  }

  @Override
  public void delete(Long id) {
    tutorRepository.delete(id);
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

  private void checkTutorBelongsToUser(Tutor tutor) {
    if (tutorRepository.existsByUser(tutor)) {
      throw new EntityExistsException(Tutor.class, "user");
    }
  }
}
