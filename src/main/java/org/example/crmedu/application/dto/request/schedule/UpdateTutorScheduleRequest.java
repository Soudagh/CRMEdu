package org.example.crmedu.application.dto.request.schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.DaysOfWeek;

/**
 * A DTO representing a request to update existing schedule.
 */
@Data
@Accessors(chain = true)
public class UpdateTutorScheduleRequest {

  private DaysOfWeek dayOfWeek;

  @NotBlank
  private String timeStart;

  @NotBlank
  private String timeEnd;
}
