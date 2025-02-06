package org.example.crmedu.domain.service;

import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;

/**
 * Service interface for managing {@link Subject} entities. Defines methods for CRUD operations and querying subjects.
 */
public interface SubjectService {

  /**
   * Creates a new subject.
   *
   * @param subject the subject to create
   * @return the created {@link Subject}
   * @throws org.example.crmedu.domain.exception.EntityExistsException if a subject with the same name already exists
   */
  Subject create(Subject subject);

  /**
   * Retrieves a subject by its unique identifier.
   *
   * @param id the unique identifier of the subject
   * @return the found {@link Subject}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no subject with the given ID is found
   */
  Subject findById(Long id);

  /**
   * Retrieves a paginated list of subjects.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of subjects per page
   * @return a {@link Page} containing the requested subjects
   */
  Page<Subject> findAll(int pageNumber, int pageSize);

  /**
   * Updates an existing subject
   *
   * @param subject the updated subject data
   * @param id the unique identifier of the subject to update
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no subject with the given ID is found
   * @throws org.example.crmedu.domain.exception.EntityExistsException if a subject with the same name already exists
   *
   */
  void update(Subject subject, Long id);

  /**
   * Delete a subject by its unique identifier.
   *
   * @param id the unique identifier of the subject to delete
   */
  void delete(Long id);
}
