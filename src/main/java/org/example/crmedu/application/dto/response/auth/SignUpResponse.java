package org.example.crmedu.application.dto.response.auth;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SignUpResponse {

  private Long id;

  private String email;

  private ZonedDateTime createdAt;
}
