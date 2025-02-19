package org.example.crmedu.domain.repository;

import java.util.Optional;
import java.util.Set;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.TutorSchedule;

/**
 * Repository interface for managing {@link TutorSchedule} entities. Defines methods for CRUD operations and querying tutors.
 */
public interface TutorScheduleRepository {

  /**
   * Saves a new tutor schedule or updates an existing one.
   *
   * @param schedule the schedule to save
   * @return the saved {@link TutorSchedule} entity
   */
  TutorSchedule save(TutorSchedule schedule);

  /**
   * Retrieves a tutor schedule by its unique identifier.
   *
   * @param id the unique identifier of the tutor schedule
   * @return an {@link Optional} containing the tutor schedule if found, otherwise empty
   */
  Optional<TutorSchedule> findById(Long id);

  /**
   * Retrieves a paginated list of schedules of certain tutor.
   *
   * @param pageNumber pageNumber the page number (starting from 0)
   * @param pageSize the number of tutors per page
   * @param tutorId the unique identifier of the tutor.
   * @return a {@link Page} containing the requested schedules
   */
  Page<TutorSchedule> findPagesByTutorId(int pageNumber, int pageSize, Long tutorId);

  /**
   * Retrieves a set of schedules of certain tutor.
   *
   * @param tutorId the unique identifier of tutor
   * @return the {@link Set<TutorSchedule>} containing the requested schedules
   */
  Set<TutorSchedule> findByTutorId(Long tutorId);

  /**
   * Updates an existing schedule.
   *
   * @param schedule the schedule with updated information.
   */
  void update(TutorSchedule schedule);

  /**
   * Deletes a schedule by its unique identifier.
   *
   * @param id the unique identifier of the schedule to delete
   */
  void delete(Long id);
}
