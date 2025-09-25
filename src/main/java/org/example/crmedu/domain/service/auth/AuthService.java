package org.example.crmedu.domain.service.auth;

import org.example.crmedu.domain.model.Jwt;
import org.example.crmedu.domain.model.User;

public interface AuthService {

  User signUp(User user);

  Jwt signIn(User user);

  void verifyEmail(String token);

  Jwt getNewAccessToken(String refreshToken);

  Jwt getNewRefreshToken(String refreshToken);
}
