package org.example.crmedu.domain.repository;

import java.util.Optional;
import org.example.crmedu.domain.model.Page;

/**
 * Base interface for repository interfaces.
 *
 * @param <T> domain model for repository
 */
public interface BaseCrudRepository<T> {

  /**
   * Creates a new object of T type.
   *
   * @param t the object to create
   * @return the created {@link T}
   */
  T create(T t);

  /**
   * Retrieves an object of type T by its unique identifier.
   *
   * @param id the unique identifier of the object T
   * @return the found {@link Optional<T>}
   */
  Optional<T> findById(Long id);

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
   */
  void update(T t);

  /**
   * Deletes an object by its unique identifier.
   *
   * @param id the unique identifier of the object to delete
   */
  void delete(Long id);
}
