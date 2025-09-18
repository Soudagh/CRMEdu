package org.example.crmedu.infrastructure.repository.organization;

import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.infrastructure.entity.OrganizationEntity;
import org.example.crmedu.infrastructure.mapping.OrganizationEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link OrganizationRepository} interface. Provides methods for managing {@link Organization} entities in the database.
 */
@Component
public class OrganizationRepositoryImpl extends BaseRepository<Organization, OrganizationEntity, Long> implements OrganizationRepository {

  private final DataOrganizationRepository organizationRepository;

  public OrganizationRepositoryImpl(DataOrganizationRepository organizationRepository,
      OrganizationEntityMapper organizationEntityMapper) {
    super(organizationRepository, organizationEntityMapper);
    this.organizationRepository = organizationRepository;
  }


  @Override
  public boolean existsByName(Organization organization) {

    return organization.getId() == null ? organizationRepository.existsByName(organization.getName())
        : organizationRepository.existsByNameAndIdIsNot(organization.getName(), organization.getId());
  }

  @Override
  public boolean existsByEmail(Organization organization) {
    return organization.getId() == null ? organizationRepository.existsByEmail(organization.getEmail())
        : organizationRepository.existsByEmailAndIdIsNot(organization.getEmail(), organization.getId());
  }

  @Override
  public boolean existsByPhone(Organization organization) {
    return organization.getId() == null ? organizationRepository.existsByPhone(organization.getPhone())
        : organizationRepository.existsByPhoneAndIdIsNot(organization.getPhone(), organization.getId());
  }

  @Override
  public boolean existsById(Long id) {
    return organizationRepository.existsById(id);
  }
}
