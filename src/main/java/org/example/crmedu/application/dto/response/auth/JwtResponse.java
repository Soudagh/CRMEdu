package org.example.crmedu.application.dto.response.auth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JwtResponse {

  private final String type = "Bearer";

  private String accessToken;

  private String refreshToken;
}
