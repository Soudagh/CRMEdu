package org.example.crmedu.application.dto.request.homework;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateHomeworkRequest {

  @NotNull(message = "Lesson ID must not be null")
  private Long lessonId;

  @NotBlank(message = "Title must not be blank")
  private String title;

  @NotBlank(message = "Description must not be blank")
  private String description;

  private String rightAnswer;

  @NotNull(message = "Start date must not be null")
  private ZonedDateTime startDate;

  private ZonedDateTime endDate;
}