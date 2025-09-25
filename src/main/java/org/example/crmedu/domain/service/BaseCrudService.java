package org.example.crmedu.domain.service;

import org.example.crmedu.domain.model.Page;

/**
 * Base interface for service classes.
 *
 * @param <T> domain model for service
 */
public interface BaseCrudService<T> {

  /**
   * Creates a new object of T type.
   *
   * @param t the object to create
   * @return the created {@link T}
   * @throws org.example.crmedu.domain.exception.EntityExistsException if object has constraints
   */
  T create(T t);

  /**
   * Retrieves an object of type T by its unique identifier.
   *
   * @param id the unique identifier of the object T
   * @return the found {@link T}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no object with the given ID is found
   */
  T findById(Long id);

  /**
   * Retrieves a paginated list of objects T.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of objects T per page
   * @return a {@link Page} containing the requested objects
   */
  Page<T> findAll(int pageNumber, int pageSize);

  /**
   * Updates an existing object.
   *
   * @param t the updated object data
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no user with the given ID is found
   * @throws org.example.crmedu.domain.exception.EntityExistsException if object has constraints
   */
  void update(T t, Long id);

  /**
   * Deletes an object by its unique identifier.
   *
   * @param id the unique identifier of the object to delete
   */
  void delete(Long id);
}
