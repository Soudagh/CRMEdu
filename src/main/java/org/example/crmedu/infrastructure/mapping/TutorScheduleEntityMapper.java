package org.example.crmedu.infrastructure.mapping;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.crmedu.domain.model.TutorSchedule;
import org.example.crmedu.infrastructure.entity.TutorScheduleEntity;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Tutor schedule domain model and Tutor schedule JPA-entity. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface TutorScheduleEntityMapper extends BaseEntityMapper<TutorSchedule, TutorScheduleEntity> {

  /**
   * Converts set of {@link TutorScheduleEntity} into {@link TutorSchedule}
   *
   * @param schedules the set of tutor schedule entities
   * @return the {@link Set<TutorSchedule>}
   */
  default Set<TutorSchedule> setTutorScheduleEntityToSetTutorSchedule(Set<TutorScheduleEntity> schedules) {
    return schedules.stream().map(this::toDomain).collect(Collectors.toSet());
  }
}
