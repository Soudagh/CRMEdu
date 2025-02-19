package org.example.crmedu.service.schedule;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.OffsetTime;
import java.util.Set;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.enums.DaysOfWeek;
import org.example.crmedu.domain.exception.TutorScheduleOverlapsException;
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
  void validateScheduleOverlap_ShouldThrowException_WhenSchedulesOverlap() {
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
  void validateScheduleOverlap_ShouldNotThrowException_WhenNoOverlap() {
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
  }
}
