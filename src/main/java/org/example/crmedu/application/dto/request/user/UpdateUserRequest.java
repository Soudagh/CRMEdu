package org.example.crmedu.application.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a request to update an existing User.
 */
@Data
@Accessors(chain = true)
public class UpdateUserRequest {

  @NotBlank(message = "Surname cannot be empty")
  @Size(max = 50, message = "Surname must be at most 50 characters")
  private String surname;

  @NotBlank(message = "Name cannot be empty")
  @Size(max = 50, message = "Name must be at most 50 characters")
  private String name;

  @Size(max = 50, message = "Patronymic must be at most 50 characters")
  private String patronymic;

  @Email(message = "Invalid email format", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
  @NotEmpty(message = "Email cannot be empty")
  @Size(max = 254)
  private String email;

  @Pattern(regexp = "^\\+\\d{1,3}\\d{10}$", message = "Invalid phone number. Expected format: +[country code]XXXXXXXXXX")
  private String phone;

  @Size(max = 50, message = "Timezone must be at most 50 characters")
  private String timezone;

  @PositiveOrZero
  private Long organization;
}
