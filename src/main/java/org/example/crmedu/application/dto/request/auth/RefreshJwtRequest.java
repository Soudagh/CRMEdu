package org.example.crmedu.application.dto.request.auth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RefreshJwtRequest {

  public String refreshToken;
}
