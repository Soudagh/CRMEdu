package org.example.crmedu.application.dto.request.organization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing the request for creating an Organization.
 */
@Data
@Accessors(chain = true)
public class CreateOrganizationRequest {

  @NotBlank(message = "Name cannot be empty")
  @Size(max = 100, message = "Name must be at most 100 characters")
  private String name;

  @NotBlank(message = "Specialization cannot be empty")
  @Size(max = 100, message = "Specialization must be at most 100 characters")
  private String specialization;

  @NotBlank(message = "Country cannot be empty")
  @Size(max = 50, message = "Country must be at most 50 characters")
  private String country;

  @Email(message = "Invalid email format", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
  @NotEmpty(message = "Email cannot be empty")
  @Size(max = 254)
  private String email;

  @Pattern(regexp = "^\\+\\d{1,3}\\d{10}$", message = "Invalid phone number. Expected format: +[country code]XXXXXXXXXX")
  private String phone;
}
