package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.organization.CreateOrganizationRequest;
import org.example.crmedu.application.dto.request.organization.UpdateOrganizationRequest;
import org.example.crmedu.application.dto.response.organization.CreateOrganizationResponse;
import org.example.crmedu.application.dto.response.organization.GetOrganizationResponse;
import org.example.crmedu.application.mapping.OrganizationDTOMapper;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing organizations. Provides endpoints to create, retrieve, update and delete organizations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/organizations")
public class OrganizationController {

  private final OrganizationService organizationService;

  private final OrganizationDTOMapper mapper;

  /**
   * Retrieves a paginated list of organizations.
   *
   * @param pageable an object specifying pagination parameters (page number and size)
   * @return return a {@link ResponseEntity} containing a paginated list of organizations wrapped in {@link PageDTO} of {@link GetOrganizationResponse}
   */
  @Secured("SUPERUSER")
  @GetMapping
  private ResponseEntity<PageDTO<GetOrganizationResponse>> getOrganizations(Pageable pageable) {
    var page = organizationService.findAll(pageable.getPageNumber(), pageable.getPageSize());
    return ResponseEntity.ok(mapper.pageOrganizationToPageDTO(page));
  }

  /**
   * Retrieves an organization by its unique identifier.
   *
   * @param id the unique identifier of the organization
   * @return a {@link ResponseEntity} containing the organization data in {@link GetOrganizationResponse}
   */
  @GetMapping("/{id}")
  private ResponseEntity<GetOrganizationResponse> getOrganization(@PathVariable Long id) {
    var organization = organizationService.findById(id);
    return ResponseEntity.ok(mapper.organizationToGetOrganizationResponse(organization));
  }

  /**
   * Creates a new organization based on the provided request data.
   *
   * @param request an object containing the organization details
   * @return a {@link ResponseEntity} with the created organization data in {@link CreateOrganizationResponse}
   */
  @PostMapping
  private ResponseEntity<CreateOrganizationResponse> createOrganization(@Valid @RequestBody CreateOrganizationRequest request) {
    var organization = organizationService.create(mapper.createRequestToOrganization(request));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.organizationToCreateResponse(organization));
  }

  /**
   * Updates an existing organization by its unique identifier.
   *
   * @param id the unique identifier of the organization to update
   * @param request an object containing the updated organization details
   * @return a {@link ResponseEntity}
   */
  @Secured({"SUPERUSER", "ORG_ADMIN"})
  @PutMapping("/{id}")
  private ResponseEntity<Void> updateOrganization(@Valid @RequestBody UpdateOrganizationRequest request, @PathVariable Long id) {
    organizationService.update(mapper.updateRequestToOrganization(request), id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a organization by its unique identifier.
   *
   * @param id the unique identifier of the organization to delete
   * @return a {@link ResponseEntity}
   */
  @Secured({"SUPERUSER", "ORG_ADMIN"})
  @DeleteMapping("/{id}")
  private ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
    organizationService.delete(id);
    return ResponseEntity.ok().build();
  }
}
