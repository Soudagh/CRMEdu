package org.example.crmedu.domain.service.tutor;

import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Tutor;

/**
 * Service interface for managing {@link Tutor} entities. Define methods for CRUD operations and querying tutors.
 */
public interface TutorService {

  /**
   * Creates a new tutor.
   *
   * @param tutor the tutor to create
   * @return the created {@link Tutor}
   * @throws org.example.crmedu.domain.exception.EntityExistsException if a tutor of current user already exist.
   */
  Tutor create(Tutor tutor);

  /**
   * Retrieves an tutor by its unique identifier.
   *
   * @param id the unique identifier of the tutor
   * @return the found {@link Tutor}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no tutor with the given ID is found
   */
  Tutor findById(Long id);

  /**
   * Retrieves a paginated list of tutors.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of tutors per page
   * @return a {@link Page} containing the requested tutors
   */
  Page<Tutor> findAll(int pageNumber, int pageSize);

  /**
   * Updates an existing tutor.
   *
   * @param tutor the updated tutor data
   * @param id the unique identifier of the tutor to update
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no tutor with the given ID is found
   * @throws org.example.crmedu.domain.exception.EntityExistsException if a tutor of current user already exist.
   */
  void update(Tutor tutor, Long id);

  /**
   * Deletes a tutor by its unique identifier.
   *
   * @param id the unique identifier of the tutor to delete
   */
  void delete(Long id);
}
