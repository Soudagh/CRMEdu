package org.example.crmedu.service.schedule;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.enums.DaysOfWeek;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.exception.TutorScheduleOverlapsException;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.model.TutorSchedule;
import org.example.crmedu.domain.repository.TutorScheduleRepository;
import org.example.crmedu.domain.service.schedule.TutorScheduleServiceImpl;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest extends BaseUnitTest {

  @InjectMocks
  private TutorScheduleServiceImpl tutorScheduleService;

  @Mock
  private TutorScheduleRepository tutorScheduleRepository;

  @Mock
  private TutorService tutorService;

  @Test
  void validateScheduleOverlap_shouldThrowException_whenSchedulesOverlap() {
    var existingSchedule = getMockObject(TutorSchedule.class)
        .setDayOfWeek(DaysOfWeek.MONDAY)
        .setTimeStart(OffsetTime.parse("10:00:00+03:00"))
        .setTimeEnd(OffsetTime.parse("12:00:00+03:00"));
    var newSchedule = getMockObject(TutorSchedule.class)
        .setDayOfWeek(DaysOfWeek.MONDAY)
        .setTimeStart(OffsetTime.parse("11:00:00+03:00"))
        .setTimeEnd(OffsetTime.parse("13:00:00+03:00"));
    var tutorId = 1L;
    when(tutorScheduleRepository.findByTutorId(tutorId))
        .thenReturn(Set.of(existingSchedule));
    assertThrows(TutorScheduleOverlapsException.class,
        () -> tutorScheduleService.createSchedule(newSchedule, tutorId));
  }

  @Test
  void validateScheduleOverlap_shouldNotThrowException_whenNoOverlap() {
    var existingSchedule = getMockObject(TutorSchedule.class)
        .setDayOfWeek(DaysOfWeek.MONDAY)
        .setTimeStart(OffsetTime.parse("10:00:00+03:00"))
        .setTimeEnd(OffsetTime.parse("12:00:00+03:00"));
    var newSchedule = getMockObject(TutorSchedule.class)
        .setDayOfWeek(DaysOfWeek.MONDAY)
        .setTimeStart(OffsetTime.parse("12:30:00+03:00"))
        .setTimeEnd(OffsetTime.parse("14:00:00+03:00"));
    var tutorId = 1L;
    when(tutorScheduleRepository.findByTutorId(tutorId))
        .thenReturn(Set.of(existingSchedule));
    assertDoesNotThrow(() -> tutorScheduleService.createSchedule(newSchedule, tutorId));
    newSchedule
        .setTimeStart(OffsetTime.parse("07:30:00+03:00"))
        .setTimeEnd(OffsetTime.parse("09:00:00+03:00"));
    assertDoesNotThrow(() -> tutorScheduleService.createSchedule(newSchedule, tutorId));
  }

  @Test
  void findById_shouldThrowException_whenSelectedIdDoesNotExist() {
    var scheduleId = 1L;
    when(tutorScheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> tutorScheduleService.findById(scheduleId));
  }

  @Test
  void findAll_shouldReturnPageOfSchedulesOfTutor() {
    int pageNumber = 0;
    int pageSize = 5;
    var tutor = getMockObject(Tutor.class);
    var schedule1 = getMockObject(TutorSchedule.class).setTutor(tutor);
    var schedule2 = getMockObject(TutorSchedule.class).setTutor(tutor);
    var schedules = List.of(schedule1, schedule2);
    var mockPage = new Page<TutorSchedule>()
        .setContent(schedules)
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalCount(schedules.size());
    var tutorId = tutor.getId();
    when(tutorScheduleRepository.findPagesByTutorId(pageNumber, pageSize, tutorId)).thenReturn(mockPage);
    var resultPages = tutorScheduleService.getTutorSchedules(pageNumber, pageSize, tutorId);
    assertNotNull(resultPages);
    assertEquals(2, resultPages.getContent().size());
    assertEquals(schedule1, resultPages.getContent().get(0));
    assertEquals(schedule2, resultPages.getContent().get(1));
  }
}
