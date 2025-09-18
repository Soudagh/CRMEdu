package org.example.crmedu.domain.service.schedule;

import java.util.Set;
import org.example.crmedu.domain.exception.TutorScheduleOverlapsException;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.TutorSchedule;
import org.example.crmedu.domain.repository.TutorScheduleRepository;
import org.example.crmedu.domain.service.BaseService;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link TutorScheduleService} interface. Provides business logic for managing {@link TutorSchedule} entities.
 */
@Service
public class TutorScheduleServiceImpl extends BaseService<TutorSchedule> implements TutorScheduleService {

  private final TutorScheduleRepository tutorScheduleRepository;

  private final TutorService tutorService;

  public TutorScheduleServiceImpl(TutorScheduleRepository tutorScheduleRepository, TutorService tutorService) {
    super(tutorScheduleRepository, TutorSchedule.class);
    this.tutorScheduleRepository = tutorScheduleRepository;
    this.tutorService = tutorService;
  }

  @Override
  public TutorSchedule createSchedule(TutorSchedule schedule, Long tutorId) {
    validateScheduleOverlap(schedule, getSchedulesByTutorId(tutorId));
    var tutor = tutorService.findById(tutorId);
    return tutorScheduleRepository.create(schedule.setTutor(tutor));
  }
  @Override
  public Page<TutorSchedule> findAll(int pageNumber, int pageSize, Long tutorId) {
    return tutorScheduleRepository.findPagesByTutorId(pageNumber, pageSize, tutorId);
  }

  @Override
  @Transactional
  public void update(TutorSchedule updatedSchedule, Long id) {
    var scheduleEntity = findById(id);
    updatedSchedule.setId(scheduleEntity.getId()).setTutor(scheduleEntity.getTutor());
    var tutorId = scheduleEntity.getTutor().getId();
    var existingSchedules = getSchedulesByTutorId(tutorId);
    validateScheduleOverlap(updatedSchedule, existingSchedules);
    tutorScheduleRepository.update(updatedSchedule.setId(id).setTutor(scheduleEntity.getTutor()));
  }

  private Set<TutorSchedule> getSchedulesByTutorId(Long tutorId) {
    return tutorScheduleRepository.findByTutorId(tutorId);
  }

  private void validateScheduleOverlap(TutorSchedule newSchedule, Set<TutorSchedule> scheduleSet) {
    var schedules = scheduleSet.stream()
        .filter(schedule -> schedule.getDayOfWeek().equals(newSchedule.getDayOfWeek()));
    if (newSchedule.getId() != null) {
      schedules = schedules.filter(schedule -> !schedule.getId().equals(newSchedule.getId()));
    }
    schedules.filter(schedule -> isOverlapped(newSchedule, schedule))
        .findFirst()
        .ifPresent(overlappedSchedule -> {
          throw new TutorScheduleOverlapsException(newSchedule, overlappedSchedule);
        });
  }

  private boolean isOverlapped(TutorSchedule newSchedule, TutorSchedule schedule) {
    return schedule.getTimeStart().isBefore(newSchedule.getTimeEnd()) && newSchedule.getTimeStart().isBefore(schedule.getTimeEnd());
  }
}
