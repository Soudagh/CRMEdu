package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.enums.UserStatus;

/**
 * A class representing user entity. This class is used as domain model.
 */
@Data
@Accessors(chain = true)
public class User {

  /**
   * The unique identifier of the user.
   */
  private Long id;

  /**
   * Surname of the user.
   */
  private String surname;

  /**
   * Name of the user.
   */
  private String name;

  /**
   * Patronymic of the user. Can be null.
   */
  private String patronymic;

  /**
   * Email of the user. Must be unique.
   */
  private String email;

  /**
   * Phone of the user. Must be unique.
   */
  private String phone;

  /**
   * The time zone of the user. The UTC by default.
   */
  private String timezone;

  /**
   * Timestamp with time zone when user was signed up.
   */
  private ZonedDateTime createdAt;

  /**
   * Timestamp with time zone when user was updated last time.
   */
  private ZonedDateTime updatedAt;

  /**
   * The set of the user roles in the system.
   */
  private Set<Role> roles = new HashSet<>();

  /**
   * Current status of user's account in the system.
   */
  private UserStatus status;

  /**
   * The organization, which current user belongs. Can be null, if current user is superuser.
   */
  private Organization organization;
}
