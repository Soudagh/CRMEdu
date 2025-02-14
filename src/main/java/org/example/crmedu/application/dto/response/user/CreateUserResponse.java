package org.example.crmedu.application.dto.response.user;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.enums.UserStatus;

/**
 * A DTO representing the response after creating a User.
 */
@Data
@Accessors(chain = true)
public class CreateUserResponse {

  private Long id;

  private String surname;

  private String name;

  private String patronymic;

  private String email;

  private String phone;

  private String timezone;

  private ZonedDateTime createdAt;

  private ZonedDateTime updatedAt;

  private Role role;

  private UserStatus status;

  private Long organization;
}
