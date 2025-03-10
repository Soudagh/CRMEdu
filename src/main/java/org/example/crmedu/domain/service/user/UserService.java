package org.example.crmedu.domain.service.user;

import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.User;

/**
 * Service interface for managing {@link User} entities. Define methods for CRUD operations and querying users.
 */
public interface UserService {

  /**
   * Creates a new user. If user has role 'tutor', a tutor corresponding to that user is also created.
   *
   * @param user the user to create
   * @return the created {@link User}
   * @throws org.example.crmedu.domain.exception.EntityExistsException if a user with the same email or phone already exists
   */
  User create(User user);

  /**
   * Retrieves an user by its unique identifier.
   *
   * @param id the unique identifier of the user
   * @return the found {@link User}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no user with the given ID is found
   */
  User findById(Long id);

  /**
   * Retrieves a user by its email.
   *
   * @param email the email of the user
   * @return the found {@link User}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no user with the given email is found
   */
  User findByEmail(String email);

  /**
   * Retrieves a user by its token.
   *
   * @param token the verification token of the user
   * @return the found {@link User}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no user with the given token is found
   */
  User findByVerificationToken(String token);

  /**
   * Retrieves a paginated list of users.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of users per page
   * @return a {@link Page} containing the requested users
   */
  Page<User> findAll(int pageNumber, int pageSize);

  /**
   * Updates an existing user.
   *
   * @param user the updated user data
   * @param id the unique identifier of the user to update
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no user with the given ID is found
   * @throws org.example.crmedu.domain.exception.EntityExistsException if a user with the same email or phone already exists
   */
  void update(User user, Long id);

  /**
   * Deletes a user by its unique identifier.
   *
   * @param id the unique identifier of the user to delete
   */
  void delete(Long id);

  /**
   * Verifies user in system by his verification token.
   *
   * @param token verification token of the user
   */
  void verifyUserByVerificationToken(String token);
}
