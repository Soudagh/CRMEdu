package org.example.crmedu.infrastructure.auth.jwt;

import io.jsonwebtoken.Claims;
import org.example.crmedu.domain.enums.Role;

public final class JwtUtils {

  public static JwtAuthentication generate(Claims claims) {
    return new JwtAuthentication()
        .setRole(getRole(claims))
        .setEmail(claims.getSubject());
  }

  private static Role getRole(Claims claims) {
    var stringRole = claims.get("role", String.class);
    return Enum.valueOf(Role.class, stringRole);
  }
}
