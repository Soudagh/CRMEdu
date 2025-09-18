package org.example.crmedu.infrastructure.repository.subject;

import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.repository.SubjectRepository;
import org.example.crmedu.infrastructure.entity.SubjectEntity;
import org.example.crmedu.infrastructure.mapping.SubjectEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link SubjectRepository} interface using JPA. Provides methods for managing {@link Subject} entities in the database.
 */
@Component
public class SubjectRepositoryImpl extends BaseRepository<Subject, SubjectEntity, Long> implements SubjectRepository {

  private final DataSubjectRepository subjectRepository;

  public SubjectRepositoryImpl(
      DataSubjectRepository subjectRepository, SubjectEntityMapper subjectMapper) {
    super(subjectRepository, subjectMapper);
    this.subjectRepository = subjectRepository;
  }

  @Override
  public boolean existsByNameAndOrganizationId(Subject subject) {
    return subjectRepository.existsByNameAndOrganizationId(subject.getName(), subject.getOrganization().getId());
  }
}
