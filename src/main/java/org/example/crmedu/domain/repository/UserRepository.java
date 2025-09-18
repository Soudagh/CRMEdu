package org.example.crmedu.domain.repository;

import java.util.Optional;
import org.example.crmedu.domain.model.User;

/**
 * Repository interface for managing {@link User} entities. Defines methods for CRUD operations and querying users.
 */
public interface UserRepository extends BaseCrudRepository<User> {

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

}
