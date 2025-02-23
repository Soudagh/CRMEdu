package org.example.crmedu.domain.service.organization;

import java.util.function.Function;
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
    return getOrganizationByIdOrThrow(id);
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
    checkExistanceById(id);
    checkOrganizationConstraints(organization);
    organizationRepository.update(organization.setId(id));
  }

  @Override
  public void delete(Long id) {
    organizationRepository.delete(id);
  }

  private Organization getOrganizationByIdOrThrow(Long id) {
    return organizationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Organization.class, id));
  }

  private void checkOrganizationConstraints(Organization organization) {
    checkOrganizationConstraint("name", organizationRepository::existsByName, organization);
    checkOrganizationConstraint("email", organizationRepository::existsByEmail, organization);
    checkOrganizationConstraint("phone", organizationRepository::existsByPhone, organization);
  }

  private void checkOrganizationConstraint(String field, Function<Organization, Boolean> checker, Organization organization) {
    if (checker.apply(organization)) {
      throw new EntityExistsException(Organization.class, field);
    }
  }
}
