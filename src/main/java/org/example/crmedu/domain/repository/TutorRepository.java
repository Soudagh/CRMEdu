package org.example.crmedu.domain.repository;

import java.util.Optional;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Tutor;

/**
 * Repository interface for managing {@link Tutor} entities. Defines methods for CRUD operations and querying tutors.
 */
public interface TutorRepository {

  /**
   * Saves a new tutor or updates an existing one.
   *
   * @param tutor the tutor to save
   * @return the saved {@link Tutor} entity
   */
  Tutor save(Tutor tutor);

  /**
   * Retrieves a tutor by its unique identifier.
   *
   * @param id the unique identifier of the tutor
   * @return an {@link Optional} containing the tutor if found, otherwise empty
   */
  Optional<Tutor> findById(Long id);

  /**
   * Retrieves a paginated list of tutors.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of tutors per page
   * @return a {@link Page} containing the requested tutors
   */
  Page<Tutor> findAll(int pageNumber, int pageSize);

  /**
   * Checks whether a tutor with the same user already exists.
   *
   * @param tutor the tutor to check
   * @return {@code true} if a tutor with the same user exists, otherwise {@code false}
   */
  boolean existsByUser(Tutor tutor);

  /**
   * Updates an existing tutor.
   *
   * @param tutor the tutor entity with updated information
   */
  void update(Tutor tutor);

  /**
   * Deletes a tutor by its unique identifier.
   *
   * @param id the unique identifier of the tutor to delete
   */
  void delete(Long id);
}
