package org.example.crmedu.infrastructure.repository.subject;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.repository.SubjectRepository;
import org.example.crmedu.infrastructure.mapping.SubjectEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link SubjectRepository} interface using JPA. Provides methods for managing {@link Subject} entities in the database.
 */
@Component
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepository {

  private final DataSubjectRepository subjectRepository;

  private final SubjectEntityMapper subjectMapper;

  @Override
  public Subject save(Subject subject) {
    var requestedSubjectEntity = subjectMapper.toSubjectEntity(subject);
    var responsedSubjectEntity = subjectRepository.save(requestedSubjectEntity);
    return subjectMapper.toSubject(responsedSubjectEntity);
  }

  @Override
  public Optional<Subject> findById(Long id) {
    return subjectRepository.findById(id)
        .map(subjectMapper::toSubject);
  }

  @Override
  public boolean existsByName(Subject subject) {
    return subjectRepository.existsByName(subject.getName());
  }

  @Override
  public Page<Subject> findAll(int pageNumber, int pageSize) {
    var page = subjectRepository.findAll(
            PageRequest.of(
                pageNumber,
                pageSize
            ))
        .map(subjectMapper::toSubject);
    return new Page<Subject>()
        .setContent(page.getContent())
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalPages(page.getTotalPages())
        .setTotalCount(page.getTotalElements());
  }

  @Override
  public void update(Subject subject) {
    subjectRepository.save(subjectMapper.toSubjectEntity(subject));
  }

  @Override
  public void delete(Long id) {
    subjectRepository.deleteById(id);
  }
}
