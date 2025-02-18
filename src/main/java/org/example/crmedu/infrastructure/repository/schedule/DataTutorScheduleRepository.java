package org.example.crmedu.infrastructure.repository.schedule;

import java.util.Set;
import org.example.crmedu.infrastructure.entity.TutorScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for managing {@link TutorScheduleEntity} entities in the database. Extends {@link JpaRepository} and {@link PagingAndSortingRepository} to
 * provide CRUD operations and pagination.
 */
@Repository
public interface DataTutorScheduleRepository extends CrudRepository<TutorScheduleEntity, Long>, PagingAndSortingRepository<TutorScheduleEntity, Long> {

  /**
   * Retrieves a paginated list of schedules by tutorId.
   *
   * @param tutorId the unique identifier that owns that schedule
   * @param pageable an object specifying pagination parameters
   * @return a {@link Page} containing requested schedules
   */
  Page<TutorScheduleEntity> findTutorScheduleEntitiesByTutor_Id(Long tutorId, Pageable pageable);

  /**
   * Retrieves set of schedules by tutorId.
   *
   * @param tutorId the unique identifier that owns that schedule
   * @return a {@link Set<TutorScheduleEntity>} containing schedules
   */
  Set<TutorScheduleEntity> findTutorScheduleEntitiesByTutor_Id(Long tutorId);
}
