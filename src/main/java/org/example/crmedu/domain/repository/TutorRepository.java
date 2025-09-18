package org.example.crmedu.domain.repository;

import org.example.crmedu.domain.model.Tutor;

/**
 * Repository interface for managing {@link Tutor} entities. Defines methods for CRUD operations and querying tutors.
 */
public interface TutorRepository extends BaseCrudRepository<Tutor> {

  /**
   * Checks whether a tutor with the same user already exists.
   *
   * @param tutor the tutor to check
   * @return {@code true} if a tutor with the same user exists, otherwise {@code false}
   */
  boolean existsByUser(Tutor tutor);

  Tutor findTutorByUserId(Long userId);
}
