package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A class representing an organization entity. This class is used as a domain model.
 */
@Data
@Accessors(chain = true)
public class Organization {

  private Long id;

  private String name;

  private String specialization;

  private String country;

  private String email;

  private String phone;
}
