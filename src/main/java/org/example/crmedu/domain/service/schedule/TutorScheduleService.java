package org.example.crmedu.domain.service.schedule;

import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.TutorSchedule;

/**
 * Service interface for managing {@link TutorSchedule} entities. Define methods for CRUD operations and querying schedules.
 */
public interface TutorScheduleService {

  /**
   * Creates a new schedule.
   *
   * @param schedule the schedule to create
   * @param tutorId the tutor unique identifier who owns the schedule
   * @return the created {@link TutorSchedule}
   * @throws org.example.crmedu.domain.exception.TutorScheduleOverlapsException if schedule overlaps another schedule.
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if tutor with given id not found.
   */
  TutorSchedule createSchedule(TutorSchedule schedule, Long tutorId);

  /**
   * Retrieves a schedule by its unique identifier.
   *
   * @param id the unique identifier of the schedule
   * @return the found {@link TutorSchedule}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if schedule with given id not found
   */
  TutorSchedule findById(Long id);

  /**
   * Retrieves a paginated list of tutor schedules.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of tutors per page
   * @param tutorId the unique identifier of the tutor that owns this schedule
   * @return a {@link Page} containing the requested schedules
   */
  Page<TutorSchedule> findAll(int pageNumber, int pageSize, Long tutorId);

  /**
   * Updates an existing schedule.
   *
   * @param schedule the updated schedule data
   * @param id the unique identifier of the schedule to update
   * @throws org.example.crmedu.domain.exception.TutorScheduleOverlapsException if schedule overlaps another schedule.
   */
  void update(TutorSchedule schedule, Long id);

  /**
   * Deletes a schedule by its unique identifier.
   *
   * @param id the unique identifier of the schedule to delete
   */
  void delete(Long id);
}
