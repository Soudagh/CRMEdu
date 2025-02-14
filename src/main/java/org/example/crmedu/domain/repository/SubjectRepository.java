package org.example.crmedu.domain.repository;

import java.util.Optional;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;

/**
 * Repository interface for managing {@link Subject} entities. Defines methods for CRUD operations and querying subjects.
 */
public interface SubjectRepository {

  /**
   * Saves a new subject or updates an existing one.
   *
   * @param subject the subject to save
   * @return the saved {@link Subject} entity
   */
  Subject save(Subject subject);

  /**
   * Retrieves a subject by its unique identifier
   *
   * @param id the unique identifier of the subject
   * @return an {@link Optional} containing the subject if found, otherwise empty
   */
  Optional<Subject> findById(Long id);

  /**
   * Checks whether a subject with the same name that belongs to certain organization already exists.
   *
   * @param subject the subject to check
   * @return {@code true} if a subject with the same name exists, otherwise {@code false}
   */
  boolean existsByNameAndOrganizationId(Subject subject);

  /**
   * Retrieves a paginated list of subjects.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of subjects per page
   * @return a {@link Page} containing the requested subjects
   */
  Page<Subject> findAll(int pageNumber, int pageSize);

  /**
   * Updates an existing subject.
   *
   * @param subject the subject entity with updated information
   */
  void update(Subject subject);

  /**
   * Deletes a subject by its unique identifier.
   *
   * @param id the unique identifier of the subject to delete
   */
  void delete(Long id);
}
