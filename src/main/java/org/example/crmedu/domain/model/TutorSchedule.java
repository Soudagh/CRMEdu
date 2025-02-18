package org.example.crmedu.domain.model;

import java.time.OffsetTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.DaysOfWeek;

/**
 * A class representing a tutor schedule entity. This class is used as a domain model. Contains information about tutor's work time.
 */
@Data
@Accessors(chain = true)
public class TutorSchedule {

  /**
   * The unique identifier of the schedule.
   */
  private Long id;

  /**
   * The day of week when tutor works.
   */
  private DaysOfWeek dayOfWeek;

  /**
   * Start of the working period.
   */
  private OffsetTime timeStart;

  /**
   * End of the working period.
   */
  private OffsetTime timeEnd;

  /**
   * The tutor, which this schedule belongs.
   */
  private Tutor tutor;
}
