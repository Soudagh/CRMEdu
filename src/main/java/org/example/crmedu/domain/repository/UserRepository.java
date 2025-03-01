package org.example.crmedu.domain.repository;

import java.util.Optional;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.User;

/**
 * Repository interface for managing {@link User} entities. Defines methods for CRUD operations and querying users.
 */
public interface UserRepository {

  /**
   * Retrieves a paginated list of users.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of the users per page
   * @return a {@link Page} containing the requested users
   */
  Page<User> findAll(int pageNumber, int pageSize);

  /**
   * Retrieves an user by its unique identifier.
   *
   * @param id the unique identifier of the user
   * @return an {@link Optional} containing the user if found, otherwise empty
   */
  Optional<User> findById(Long id);

  /**
   * Retrieves a user by its email.
   *
   * @param email the email of the user
   * @return an {@link Optional} containing the user if found, otherwise empty
   */
  Optional<User> findByEmail(String email);

  /**
   * Retrieves a user by its verification token.
   *
   * @param token the verification token of the user
   * @return an {@link Optional} containing the user if found, otherwise empty
   */
  Optional<User> findByVerificationToken(String token);

  /**
   * Checks whether a user with the same email already exists.
   *
   * @param user the user to check
   * @return {@code true} if a user with the same email exists, otherwise {@code false}
   */
  boolean existsByEmail(User user);

  /**
   * Checks whether a user with the same phone already exists.
   *
   * @param user the user to check
   * @return {@code true} if a user with the same phone exists, otherwise {@code false}
   */
  boolean existsByPhone(User user);

  /**
   * Saves a new user or updates an existing one.
   *
   * @param user the user to save
   * @return the saved {@link User} entity
   */
  User save(User user);

  /**
   * Updates an existing user.
   *
   * @param user the user entity with updated information
   */
  void update(User user);

  /**
   * Deletes an user by its unique identifier.
   *
   * @param id the unique identifier of the subject to delete.
   */
  void delete(Long id);
}
