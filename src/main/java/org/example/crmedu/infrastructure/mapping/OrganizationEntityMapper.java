package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.infrastructure.entity.OrganizationEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Organization domain model and Organization JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface OrganizationEntityMapper extends BaseEntityMapper<Organization, OrganizationEntity> {


}
