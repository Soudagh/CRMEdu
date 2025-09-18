package org.example.crmedu.domain.repository;

import org.example.crmedu.domain.model.Subject;

/**
 * Repository interface for managing {@link Subject} entities. Defines methods for CRUD operations and querying subjects.
 */
public interface SubjectRepository extends BaseCrudRepository<Subject> {

  /**
   * Checks whether a subject with the same name that belongs to certain organization already exists.
   *
   * @param subject the subject to check
   * @return {@code true} if a subject with the same name exists, otherwise {@code false}
   */
  boolean existsByNameAndOrganizationId(Subject subject);
}
