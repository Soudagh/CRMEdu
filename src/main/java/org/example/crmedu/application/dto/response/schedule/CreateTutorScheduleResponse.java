package org.example.crmedu.application.dto.response.schedule;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.DaysOfWeek;

/**
 * A DTO representing the response with creating a tutor schedule.
 */
@Data
@Accessors(chain = true)
public class CreateTutorScheduleResponse {

  private Long id;

  private Long tutorId;

  private DaysOfWeek dayOfWeek;

  private String timeStart;

  private String timeEnd;
}
