package org.example.crmedu.domain.service.subject;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.domain.repository.SubjectRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link SubjectService} interface. Provides business logic for managing {@link Subject} entities.
 */
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

  private final SubjectRepository subjectRepository;

  private final OrganizationRepository organizationRepository;

  @Override
  public Subject create(Subject subject) {
    checkSubjectConstraints(subject);
    if (organizationRepository.existsById(subject.getOrganization().getId())) {
      return subjectRepository.save(subject);
    }
    throw new EntityNotFoundException(Organization.class, subject.getOrganization().getId());
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
    checkSubjectConstraints(subject);
    subjectRepository.update(subject
        .setId(subjectEntity.getId())
        .setOrganization(subjectEntity.getOrganization())
    );
  }

  @Override
  public void delete(Long id) {
    subjectRepository.delete(id);
  }

  private void checkSubjectConstraints(Subject subject) {
    if (subjectRepository.existsByNameAndOrganizationId(subject)) {
      throw new EntityExistsException(Subject.class, "name and organization");
    }
  }
}
