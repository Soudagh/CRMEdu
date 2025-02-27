package org.example.crmedu.infrastructure.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.service.jwt.PasswordEncode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncodeImpl implements PasswordEncode {

  private final PasswordEncoder passwordEncoder;

  @Override
  public String encode(String password) {
    return passwordEncoder.encode(password);
  }
}
