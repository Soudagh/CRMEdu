package org.example.crmedu.domain.service.subject;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.repository.SubjectRepository;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link SubjectService} interface. Provides business logic for managing {@link Subject} entities.
 */
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

  private final SubjectRepository subjectRepository;

  private final OrganizationService organizationService;

  @Override
  public Subject create(Subject subject) {
    checkSubjectConstraints(subject);
    organizationService.checkExistanceById(subject.getOrganization().getId());
    return subjectRepository.save(subject);
  }

  @Override
  @Transactional
  public Subject findById(Long id) {
    return subjectRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Subject.class, id));
  }

  @Override
  public Page<Subject> findAll(int pageNumber, int pageSize) {
    return subjectRepository.findAll(pageNumber, pageSize);
  }

  @Override
  @Transactional
  public void update(Subject subject, Long id) {
    var subjectEntity = findById(id);
    subject
        .setId(subjectEntity.getId())
        .setOrganization(subjectEntity.getOrganization());
    checkSubjectConstraints(subject);
    subjectRepository.update(subject);
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
