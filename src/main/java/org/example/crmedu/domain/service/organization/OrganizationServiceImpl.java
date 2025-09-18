package org.example.crmedu.domain.service.organization;

import java.util.ArrayList;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.exception.UniqueConstraintsException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.exception.UniqueConstraintError;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.domain.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link OrganizationService} interface. Provides business logic for managing {@link Organization} entities.
 */
@Service
public class OrganizationServiceImpl extends BaseService<Organization> implements OrganizationService {

  private final OrganizationRepository organizationRepository;

  public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
    super(organizationRepository, Organization.class);
    this.organizationRepository = organizationRepository;
  }

  @Override
  public Organization create(Organization organization) {
    checkOrganizationUniqueConstraints(organization);
    return organizationRepository.create(organization);
  }

  @Override
  public void checkExistanceById(Long id) {
    if (!organizationRepository.existsById(id)) {
      throw new EntityNotFoundException(Organization.class, id);
    }
  }

  @Override
  public void update(Organization organization, Long id) {
    checkExistanceById(id);
    organization.setId(id);
    checkOrganizationUniqueConstraints(organization);
    organizationRepository.update(organization);
  }

  private void checkOrganizationUniqueConstraints(Organization organization) {
    var violations = new ArrayList<UniqueConstraintError>();
    if (organizationRepository.existsByName(organization)) {
      violations.add(new UniqueConstraintError("name", organization.getName()));
    }
    if (organizationRepository.existsByEmail(organization)) {
      violations.add(new UniqueConstraintError("email", organization.getEmail()));
    }
    if (organizationRepository.existsByPhone(organization)) {
      violations.add(new UniqueConstraintError("phone", organization.getPhone()));
    }
    if (!violations.isEmpty()) {
      throw new UniqueConstraintsException(Organization.class, violations);
    }
  }
}
