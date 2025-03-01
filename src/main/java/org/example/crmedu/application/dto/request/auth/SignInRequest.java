package org.example.crmedu.application.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a request to sign in.
 */
@Data
@Accessors(chain = true)
public class SignInRequest {

  @Email(message = "Invalid email format", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
  @NotEmpty(message = "Email cannot be empty")
  @Size(max = 254)
  private String email;

  @NotEmpty(message = "Password cannot be empty")
  private String password;
}
