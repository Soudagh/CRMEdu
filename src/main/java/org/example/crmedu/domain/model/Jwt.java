package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Jwt {

  private String type;

  private String accessToken;

  private String refreshToken;
}
