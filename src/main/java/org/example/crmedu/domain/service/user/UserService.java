package org.example.crmedu.domain.service.user;

import java.util.List;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.BaseCrudService;

/**
 * Service interface for managing {@link User} entities. Define methods for CRUD operations and querying users.
 */
public interface UserService extends BaseCrudService<User> {

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
   * Verifies user in system by his verification token.
   *
   * @param token verification token of the user
   */
  void verifyUserByVerificationToken(String token);

  List<Lesson> getUserSchedule(User user);

  void updateNotificationsMode(User user, Boolean newMode);
}
