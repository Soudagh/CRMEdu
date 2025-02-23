package org.example.crmedu.application.dto.response.schedule;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.DaysOfWeek;

/**
 * A DTO representing the response with retrieving a tutor schedule.
 */
@Data
@Accessors(chain = true)
public class GetTutorScheduleResponse {

  private Long id;

  private DaysOfWeek dayOfWeek;

  private String timeStart;

  private String timeEnd;
}
