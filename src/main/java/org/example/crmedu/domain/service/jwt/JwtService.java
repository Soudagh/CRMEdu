package org.example.crmedu.domain.service.jwt;

import org.example.crmedu.domain.model.Jwt;
import org.example.crmedu.domain.model.User;

/**
 * Service interface for managing operations with authentication.
 */
public interface JwtService {

  /**
   * Logs in selected user in system.
   *
   * @param user a {@link User} that needs to log in
   * @return a {@link Jwt} model containing details about tokens
   */
  Jwt login(User user);

  /**
   * Retrieves access token via refresh token.
   *
   * @param refreshToken the refresh token of selected user
   * @return a {@link Jwt} model containing details about tokens
   */
  Jwt getAccessToken(String refreshToken);

  /**
   * Updates and retrieves information about tokens via old refreshToken.
   *
   * @param refreshToken the refresh token of selected user
   * @return {@link Jwt} model containing details about updated tokens
   */
  Jwt refresh(String refreshToken);

  User getCurrentUser();
}
