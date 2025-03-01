package org.example.crmedu.application.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a request to refresh access or refresh token.
 */
@Data
@Accessors(chain = true)
public class RefreshJwtRequest {

  @NotEmpty(message = "Refresh token cannot be empty")
  public String refreshToken;
}
