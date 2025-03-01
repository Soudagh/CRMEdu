package org.example.crmedu.infrastructure.auth.jwt;

import io.jsonwebtoken.Claims;
import org.example.crmedu.domain.enums.Role;

/**
 * Utility class that provides methods to extract user details, such as email and role, from JWT claims.
 */
public final class JwtUtils {

  /**
   * Generates a {@link JwtAuthentication} object from JWT claims.
   *
   * @param claims the JWT claims containing user details
   * @return a populated {@link JwtAuthentication} object
   */
  public static JwtAuthentication generate(Claims claims) {
    return new JwtAuthentication()
        .setRole(getRole(claims))
        .setEmail(claims.getSubject());
  }

  /**
   * Extracts the {@link Role} from JWT claims.
   *
   * @param claims the JWT claims
   * @return the user role
   */
  private static Role getRole(Claims claims) {
    var stringRole = claims.get("role", String.class);
    return Enum.valueOf(Role.class, stringRole);
  }
}
