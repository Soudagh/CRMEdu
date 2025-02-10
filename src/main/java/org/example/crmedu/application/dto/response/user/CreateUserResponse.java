package org.example.crmedu.application.dto.response.user;

import java.time.ZonedDateTime;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.enums.UserStatus;
import org.example.crmedu.domain.model.Organization;

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

  private Set<Role> roles;

  private UserStatus status;

  private Organization organization;
}
