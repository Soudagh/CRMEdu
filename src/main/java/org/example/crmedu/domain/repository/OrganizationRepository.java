package org.example.crmedu.domain.repository;

import java.util.Optional;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;

/**
 * Repository class for managing {@link Organization} entities. Defines methods for CRUD operations and querying subjects.
 */
public interface OrganizationRepository {

  /**
   * Saves a new organization or updates an existing one.
   *
   * @param organization the organization to save
   * @return the saved {@link Organization} entity
   */
  Organization save(Organization organization);

  /**
   * Checks whether an organization with the same name already exists.
   *
   * @param organization the organization to check
   * @return {@code true} if an organization with the same name exists, otherwise {@code false}
   */
  boolean existsByName(Organization organization);

  /**
   * Checks whether an organization with the same email already exists.
   *
   * @param organization the organization to check
   * @return {@code true} if an organization with the same email exists, otherwise {@code false}
   */
  boolean existsByEmail(Organization organization);

  /**
   * Checks whether an organization with the same phone already exists.
   *
   * @param organization the organization to check
   * @return {@code true} if an organization with the same phone exists, otherwise {@code false}
   */
  boolean existsByPhone(Organization organization);

  /**
   * Checks whether an organization with this identifier exists.
   *
   * @param id the unique identifier to check
   * @return {@code true} if an organization with id exists, otherwise {@code false}
   */
  boolean existsById(Long id);

  /**
   * Retrieves an organization by its unique identifier.
   *
   * @param id the unique identifier of the organization
   * @return an {@link Optional} containing the organization if fount, otherwise empty
   */
  Optional<Organization> findById(Long id);

  /**
   * Retrieves a paginated list of organizations.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of organizations per page
   * @return a {@link Page} containing the requested organizations
   */
  Page<Organization> findAll(int pageNumber, int pageSize);

  /**
   * Updates an existing organization.
   *
   * @param organization the organization entity with updated information
   */
  void update(Organization organization);

  /**
   * Delete an organization by its unique identifier.
   *
   * @param id the unique identifier of the organization to delete
   */
  void delete(Long id);
}
