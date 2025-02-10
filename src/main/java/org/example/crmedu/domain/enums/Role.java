package org.example.crmedu.domain.enums;

/**
 * An enumeration class, representing the user roles in the system.
 */
public enum Role {

  /**
   * Role that has most rights and access.
   */
  SUPERUSER,

  /**
   * Role that has all rights and access in bounds of one organization.
   */
  ORG_ADMIN,

  /**
   * Role that has right to watch the schedule of the lessons and make notes about them.
   */
  TUTOR,

  /**
   * Role that has rights to manage the schedule of tutors.
   */
  CURATOR,

  /**
   * Role that has no rights in the system. Could be an account, which status is "pending".
   */
  USER
}
