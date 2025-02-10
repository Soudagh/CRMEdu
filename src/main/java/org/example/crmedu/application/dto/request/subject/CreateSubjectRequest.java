package org.example.crmedu.application.dto.request.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a request to create a new Subject.
 */
@Data
@Accessors(chain = true)
public class CreateSubjectRequest {

  @NotBlank(message = "Name cannot be empty")
  @Size(max = 50, message = "Name must be at most 100 characters")
  private String name;

  @NotNull
  @PositiveOrZero
  private Long organization;
}
