package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
   * The password of the user.
   */
  private String password;

  /**
   * The verification token of user. Needs for verify account via email.
   */
  private String verificationToken;

  /**
   * Timestamp with time zone when user was signed up.
   */
  private ZonedDateTime createdAt;

  /**
   * Timestamp withкак  time zone when user was updated last time.
   */
  private ZonedDateTime updatedAt;

  /**
   * The set of the user roles in the system.
   */
  private Role role;

  /**
   * Current status of user's account in the system.
   */
  private UserStatus status;

  private Boolean notificationsEnabled = true;

  private String hex;

  /**
   * The organization, which current user belongs. Can be null, if current user is superuser.
   */
  private Organization organization;

  private List<Notification> notifications = new ArrayList<>();
}
