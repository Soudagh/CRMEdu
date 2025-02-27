package org.example.crmedu.domain.service.jwt;

import org.example.crmedu.domain.model.Jwt;
import org.example.crmedu.domain.model.User;

public interface JwtService {

  Jwt login(User user);

  Jwt getAccessToken(String refreshToken);

  Jwt refresh(String refreshToken);

  User getCurrentUser();
}
