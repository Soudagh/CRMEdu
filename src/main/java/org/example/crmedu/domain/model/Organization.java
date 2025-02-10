package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A class representing an organization entity. This class is used as a domain model.
 */
@Data
@Accessors(chain = true)
public class Organization {

  /**
   * The unique identifier of the organization.
   */
  private Long id;

  /**
   * The name of the organization. Must be unique.
   */
  private String name;

  /**
   * The specialization of the organization.
   */
  private String specialization;

  /**
   * Country where organization placed.
   */
  private String country;

  /**
   * An email of the organization. Must be unique.
   */
  private String email;

  /**
   * Phone of the organization. Must be unique
   */
  private String phone;
}
