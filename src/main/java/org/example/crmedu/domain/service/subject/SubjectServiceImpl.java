package org.example.crmedu.domain.service.subject;

import java.util.ArrayList;
import org.example.crmedu.domain.exception.UniqueConstraintsException;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.exception.UniqueConstraintError;
import org.example.crmedu.domain.repository.SubjectRepository;
import org.example.crmedu.domain.service.BaseService;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link SubjectService} interface. Provides business logic for managing {@link Subject} entities.
 */
@Service
public class SubjectServiceImpl extends BaseService<Subject> implements SubjectService {

  private final SubjectRepository subjectRepository;

  private final OrganizationService organizationService;

  public SubjectServiceImpl(SubjectRepository subjectRepository, OrganizationService organizationService) {
    super(subjectRepository, Subject.class);
    this.subjectRepository = subjectRepository;
    this.organizationService = organizationService;
  }

  @Override
  public Subject create(Subject subject) {
    checkSubjectConstraints(subject);
    organizationService.checkExistanceById(subject.getOrganization().getId());
    return subjectRepository.create(subject);
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

  private void checkSubjectConstraints(Subject subject) {
    var violations = new ArrayList<UniqueConstraintError>();
    if (subjectRepository.existsByNameAndOrganizationId(subject)) {
      violations.add(new UniqueConstraintError("name", subject.getName()));
    }
    if (!violations.isEmpty()) {
      throw new UniqueConstraintsException(Subject.class, violations);
    }
  }
}
