package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A class representing a structure of Jwt object for specific user.
 */
@Data
@Accessors(chain = true)
public class Jwt {

  /**
   * Type of token. Default - "Bearer".
   */
  private String type;

  /**
   * The user's current token.
   */
  private String accessToken;

  /**
   * The user's current refresh token.
   */
  private String refreshToken;
}
