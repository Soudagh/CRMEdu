package org.example.crmedu.domain.service.organization;

import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;

/**
 * Service interface for managing {@link Organization} entities. Defines methods for CRUD operations and querying organizations.
 */
public interface OrganizationService {

  /**
   * Creates a new organization.
   *
   * @param organization the organization to create
   * @return the created {@link Organization}
   */
  Organization create(Organization organization);

  /**
   * Retrieves an organization by its unique identifier.
   *
   * @param id the unique identifier of the organization
   * @return the found {@link Organization}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no organization with the given ID is found
   */
  Organization findById(Long id);

  /**
   * Check if organization with this unique identifier exists.
   *
   * @param id the unique identifier of the organization
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no organization with the given ID is found
   */
  void checkExistanceById(Long id);

  /**
   * Retrieves a paginated list of organizations.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of organizations per page
   * @return a {@link Page} containing the requested subjects
   */
  Page<Organization> findAll(int pageNumber, int pageSize);

  /**
   * Updates an existing organization.
   *
   * @param organization the updated organization data
   * @param id the unique identifier of the organization
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no organization with the given ID is found
   */
  void update(Organization organization, Long id);

  /**
   * Deletes an organization by its unique identifier.
   *
   * @param id the unique identifier of the organization to delete
   */
  void delete(Long id);
}
