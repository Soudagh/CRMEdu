package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.infrastructure.entity.OrganizationEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Organization domain model and Organization JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface OrganizationEntityMapper {

  /**
   * Converts an {@link Organization} to {@link OrganizationEntity}.
   *
   * @param organization the organization domain model to convert
   * @return the {@link OrganizationEntity} JPA-entity
   */
  OrganizationEntity toOrganizationEntity(Organization organization);

  /**
   *
   * Converts an {@link OrganizationEntity} to {@link Organization}.
   *
   * @param organizationEntity the organization entity to convert
   * @return the {@link Organization} domain model
   */
  Organization toOrganization(OrganizationEntity organizationEntity);
}
