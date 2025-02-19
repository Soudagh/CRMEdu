package org.example.crmedu.application.dto.request.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.StudentStatus;

/**
 * A DTO representing a request to create a new Student.
 */
@Data
@Accessors(chain = true)
public class CreateStudentRequest {

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
  @NotEmpty(message = "Phone cannot be empty")
  private String phone;

  @Size(max = 50, message = "Timezone must be at most 50 characters")
  private String timezone;

  @Size(max = 7, message = "Hex must be in format #FFFFFF")
  private String hex;

  private Date birthDate;

  @NotNull(message = "Grade must not be null")
  @Max(11)
  @Min(1)
  private Integer grade;

  @PositiveOrZero
  private Integer balance;

  private StudentStatus status;

  @PositiveOrZero
  private Long organization;
}
