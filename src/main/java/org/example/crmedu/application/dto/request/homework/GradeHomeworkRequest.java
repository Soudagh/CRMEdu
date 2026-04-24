package org.example.crmedu.application.dto.request.homework;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.CompletedTaskStatus;

@Data
@Accessors(chain = true)
public class GradeHomeworkRequest {

  @NotNull(message = "Status must not be null")
  private CompletedTaskStatus status;

  private String comments;
}