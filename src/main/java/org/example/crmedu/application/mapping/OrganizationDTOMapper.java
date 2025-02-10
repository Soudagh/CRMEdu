package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.organization.CreateOrganizationRequest;
import org.example.crmedu.application.dto.request.organization.UpdateOrganizationRequest;
import org.example.crmedu.application.dto.response.organization.CreateOrganizationResponse;
import org.example.crmedu.application.dto.response.organization.GetOrganizationResponse;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * A mapper interface for converting between Organization domain model and its corresponding DTOs. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationDTOMapper {

  /**
   * Converts an {@link Organization} entity to a {@link GetOrganizationResponse} DTO.
   *
   * @param organization the organization model to convert
   * @return the corresponding {@link GetOrganizationResponse} DTO
   */
  GetOrganizationResponse organizationToGetOrganizationResponse(Organization organization);

  /**
   * Converts a paginated {@link Page} of {@link Organization} entities to a paginated {@link PageDTO} of {@link GetOrganizationResponse} DTOs.
   *
   * @param page the paginated organization entities
   * @return a paginated response DTO containing organization data
   */
  PageDTO<GetOrganizationResponse> pageOrganizationToPageDTO(Page<Organization> page);

  /**
   * Converts a {@link CreateOrganizationRequest} DTO to a {@link Organization} model.
   *
   * @param request the DTO containing organization creation details
   * @return the corresponding {@link Organization} model
   */
  Organization createRequestToOrganization(CreateOrganizationRequest request);

  /**
   * Converts an {@link UpdateOrganizationRequest} DTO to a {@link Organization} model.
   *
   * @param request the DTO containing updated organization details
   * @return the corresponding {@link Organization} model
   */
  Organization updateRequestToOrganization(UpdateOrganizationRequest request);

  /**
   * Converts a {@link Organization} model to a {@link CreateOrganizationResponse} DTO.
   *
   * @param organization the created organization model
   * @return the corresponding {@link CreateOrganizationResponse} DTO
   */
  CreateOrganizationResponse organizationToCreateResponse(Organization organization);

  /**
   * Converts the unique identifier of the organization of the subject to {@link Organization} model.
   *
   * @param id the unique identifier of organization
   * @return the corresponding {@link Organization} model
   */
  Organization idToOrganization(Long id);
}
