package org.example.crmedu.infrastructure.auth.jwt;

import java.util.Collection;
import java.util.List;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Represents an authentication object for JWT-based security.
 */
@Data
@Accessors(chain = true)
public class JwtAuthentication implements Authentication {

  private boolean authenticated;

  private String email;

  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return email;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  @SneakyThrows
  public void setAuthenticated(boolean isAuthenticated) {
    authenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof JwtAuthentication other)) {
      return false;
    }
    return email != null && email.equals(other.email);
  }

  @Override
  public String toString() {
    return email;
  }
}
