package org.example.crmedu.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.repository.SubjectRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link SubjectService} interface. Provides business logic for managing {@link Subject} entities.
 */
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

  private final SubjectRepository subjectRepository;

  @Override
  public Subject create(Subject subject) {
    if (subjectRepository.existsByName(subject)) {
      throw new EntityExistsException(Subject.class, "name");
    }
    return subjectRepository.save(subject);
  }

  @Override
  public Subject findById(Long id) {
    return subjectRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Subject.class, id));
  }

  @Override
  public Page<Subject> findAll(int pageNumber, int pageSize) {
    return subjectRepository.findAll(pageNumber, pageSize);
  }

  @Override
  public void update(Subject subject, Long id) {
    var subjectEntity = subjectRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Subject.class, id));
    subjectRepository.update(subject
        .setId(subjectEntity.getId())
        .setName(subject.getName())
    );
  }

  @Override
  public void delete(Long id) {
    subjectRepository.delete(id);
  }
}
