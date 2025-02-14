package org.example.crmedu.infrastructure.repository.organization;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.infrastructure.mapping.OrganizationEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link OrganizationRepository} interface. Provides methods for managing {@link Organization} entities in the database.
 */
@Component
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {

  private final DataOrganizationRepository organizationRepository;

  private final OrganizationEntityMapper mapper;

  @Override
  public Organization save(Organization organization) {
    var requestedOrganizationEntity = mapper.toOrganizationEntity(organization);
    var responsedOrganizationEntity = organizationRepository.save(requestedOrganizationEntity);
    return mapper.toOrganization(responsedOrganizationEntity);
  }

  @Override
  public boolean existsByName(Organization organization) {
    return organizationRepository.existsByName(organization.getName());
  }

  @Override
  public boolean existsByEmail(Organization organization) {
    return organizationRepository.existsByEmail(organization.getEmail());
  }

  @Override
  public boolean existsByPhone(Organization organization) {
    return organizationRepository.existsByPhone(organization.getPhone());
  }

  @Override
  public boolean existsById(Long id) {
    return organizationRepository.existsById(id);
  }

  @Override
  public Optional<Organization> findById(Long id) {
    return organizationRepository.findById(id)
        .map(mapper::toOrganization);
  }

  @Override
  public Page<Organization> findAll(int pageNumber, int pageSize) {
    var page = organizationRepository.findAll(
        PageRequest.of(
            pageNumber,
            pageSize
        )
    ).map(mapper::toOrganization);
    return new Page<Organization>()
        .setContent(page.getContent())
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalPages(page.getTotalPages())
        .setTotalCount(page.getTotalElements());
  }

  @Override
  public void update(Organization organization) {
    organizationRepository.save(mapper.toOrganizationEntity(organization));
  }

  @Override
  public void delete(Long id) {
    organizationRepository.deleteById(id);
  }
}
