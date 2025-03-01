package org.example.crmedu.domain.service.jwt;

/**
 * Component interface for define methods for encode passwords.
 */
public interface PasswordEncode {

  /**
   * Encode given password.
   *
   * @param password the password to encode
   * @return encoded password
   */
  String encode(String password);
}
