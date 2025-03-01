package org.example.crmedu.domain.service.auth;

import org.example.crmedu.domain.model.User;

/**
 * Service interface for creating verification token of the user.
 */
public interface VerificationTokenService {

  /**
   * Creates verification token for selected user after registration.
   *
   * @param user user that needs verification token
   * @param token the random token to set
   */
  void createVerificationToken(User user, String token);
}
