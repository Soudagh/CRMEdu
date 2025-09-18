package org.example.crmedu.application.dto.request.student;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = "User ID must not be null")
  private Long userId;

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
}

