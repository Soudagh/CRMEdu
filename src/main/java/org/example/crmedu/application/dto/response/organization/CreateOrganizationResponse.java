package org.example.crmedu.application.dto.response.organization;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing the response with creating an Organization.
 */
@Data
@Accessors(chain = true)
public class CreateOrganizationResponse {

  private Long id;

  private String name;

  private String specialization;

  private String country;

  private String email;

  private String phone;
}
