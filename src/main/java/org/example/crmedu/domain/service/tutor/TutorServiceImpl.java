package org.example.crmedu.domain.service.tutor;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.TutorRepository;
import org.example.crmedu.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link TutorService} interface. Provides business logic for managing {@link Tutor} entities.
 */
@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

  private final TutorRepository tutorRepository;

  private final UserRepository userRepository;

  @Override
  public Tutor create(Tutor tutor) {
    makeChecksForCreation(tutor);
    return tutorRepository.save(tutor);
  }

  @Override
  public Tutor findById(Long id) {
    return tutorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Tutor.class, id));
  }

  @Override
  public Page<Tutor> findAll(int pageNumber, int pageSize) {
    return tutorRepository.findAll(pageNumber, pageSize);
  }

  @Override
  public void update(Tutor tutor, Long id) {
    var tutorEntity = tutorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Tutor.class, id));
    tutorRepository.update(tutor.setUser(tutorEntity.getUser()));
  }

  @Override
  public void delete(Long id) {
    tutorRepository.delete(id);
  }

  private void makeChecksForCreation(Tutor tutor) {
    checkUserExists(tutor.getUser());
    checkTutorBelongsToUser(tutor);
  }

  private void checkUserExists(User user) {
    if (!userRepository.existsById(user)) {
      throw new EntityNotFoundException(User.class, "tutor");
    }
  }

  private void checkTutorBelongsToUser(Tutor tutor) {
    if (tutorRepository.existsByUser(tutor)) {
      throw new EntityExistsException(Tutor.class, "user");
    }
  }
}
