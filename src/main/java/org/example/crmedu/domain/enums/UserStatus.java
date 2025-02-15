package org.example.crmedu.domain.enums;

/**
 * An enumeration class, representing the user's account statuses in the system.
 */
public enum UserStatus {
  /**
   * Status representing an active account, that can use the system.
   */
  ACTIVE,

  /**
   * Status representing a disabled account, that cannot use the system.
   */
  DISABLED,

  /**
   * Status representing an account, that waits of confirmation.
   */
  PENDING
}
