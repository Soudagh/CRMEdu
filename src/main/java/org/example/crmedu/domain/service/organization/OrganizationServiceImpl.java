package org.example.crmedu.domain.service.organization;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link OrganizationService} interface. Provides business logic for managing {@link Organization} entities.
 */
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

  private final OrganizationRepository organizationRepository;

  @Override
  public Organization create(Organization organization) {
    checkOrganizationConstraints(organization);
    return organizationRepository.save(organization);
  }

  @Override
  public Organization findById(Long id) {
    return organizationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Organization.class, id));
  }

  @Override
  public void checkExistanceById(Long id) {
    if (!organizationRepository.existsById(id)) {
      throw new EntityNotFoundException(Organization.class, id);
    }
  }

  @Override
  public Page<Organization> findAll(int pageNumber, int pageSize) {
    return organizationRepository.findAll(pageNumber, pageSize);
  }

  @Override
  public void update(Organization organization, Long id) {
    if (organizationRepository.existsById(id)) {
      checkOrganizationConstraints(organization);
      organizationRepository.update(organization.setId(id));
    }
    throw new EntityNotFoundException(Organization.class, id);
  }

  @Override
  public void delete(Long id) {
    organizationRepository.delete(id);
  }

  private void checkOrganizationConstraints(Organization organization) {
    if (organizationRepository.existsByName(organization)) {
      throw new EntityExistsException(Organization.class, "name");
    }
    if (organizationRepository.existsByEmail(organization)) {
      throw new EntityExistsException(Organization.class, "email");
    }
    if (organizationRepository.existsByPhone(organization)) {
      throw new EntityExistsException(Organization.class, "phone");
    }
  }
}
