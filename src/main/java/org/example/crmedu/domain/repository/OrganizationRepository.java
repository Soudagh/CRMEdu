package org.example.crmedu.domain.repository;

import org.example.crmedu.domain.model.Organization;

/**
 * Repository class for managing {@link Organization} entities. Defines methods for CRUD operations and querying subjects.
 */
public interface OrganizationRepository extends BaseCrudRepository<Organization> {

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
}
