package org.example.crmedu.domain.model;

import java.time.OffsetTime;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A class representing a tutor schedule entity. This class is used as a domain model. Contains information about tutor's work time.
 */
@Data
@Accessors(chain = true)
public class TutorSchedule {

  /**
   * Tutor that has this schedule.
   */
  private Tutor tutor;

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
}
