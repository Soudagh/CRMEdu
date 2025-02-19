package org.example.crmedu.domain.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.model.TutorSchedule;

/**
 * Exception thrown when a new schedule period overlaps with an existing one.
 */
@Setter
@Getter
@Accessors(chain = true)
public class TutorScheduleOverlapsException extends RuntimeException {

  /**
   * The message template used for the exception.
   */
  private static final String TUTOR_SCHEDULE_OVERLAPS = "New schedule period %s - %s overlaps with existing period %s - %s for tutor with id %d";

  /**
   * The new schedule that the user is trying to add.
   */
  private final TutorSchedule newSchedule;

  /**
   * The existing schedule that conflicts with the new schedule.
   */
  private final TutorSchedule overlappedSchedule;

  /**
   * Constructs a new {@code TutorScheduleOverlapsException} with a formatted message.
   *
   * @param newSchedule the new schedule attempting to be added
   * @param overlappedSchedule the existing schedule that causes the conflict
   */
  public TutorScheduleOverlapsException(TutorSchedule newSchedule, TutorSchedule overlappedSchedule) {
    super(TUTOR_SCHEDULE_OVERLAPS.formatted(
        newSchedule.getTimeStart(), newSchedule.getTimeEnd(),
        overlappedSchedule.getTimeStart(), overlappedSchedule.getTimeEnd(),
        overlappedSchedule.getTutor().getId()
    ));
    this.newSchedule = newSchedule;
    this.overlappedSchedule = overlappedSchedule;
  }
}
