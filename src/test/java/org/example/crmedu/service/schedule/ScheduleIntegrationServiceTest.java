package org.example.crmedu.service.schedule;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.model.TutorSchedule;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.schedule.TutorScheduleService;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.example.crmedu.domain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleIntegrationServiceTest extends BaseIntegrationTest {

  @Autowired
  private TutorScheduleService tutorScheduleService;

  @Autowired
  private TutorService tutorService;

  @Autowired
  private OrganizationService organizationService;

  @Autowired
  private UserService userService;

  @Test
  void delete_shouldDeleteEntityWithSelectedId() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    assertNotNull(organizationEntity);
    var user = getMockObject(User.class).setRole(Role.SUPERUSER).setId(null).setOrganization(organizationEntity);
    var userEntity = userService.create(user);
    assertNotNull(userEntity);
    var tutor = getMockObject(Tutor.class).setId(null).setUser(userEntity).setSubjects(null);
    var tutorEntity = tutorService.create(tutor);
    assertNotNull(tutorEntity);
    var schedule = getMockObject(TutorSchedule.class).setId(null).setTutor(null);
    var scheduleEntity = tutorScheduleService.createSchedule(schedule, tutorEntity.getId());
    assertNotNull(scheduleEntity);
    var scheduleId = scheduleEntity.getId();
    assertDoesNotThrow(() -> tutorScheduleService.findById(scheduleId));
    tutorScheduleService.delete(scheduleId);
    assertThrows(EntityNotFoundException.class, () -> tutorScheduleService.findById(scheduleId));
  }

  @Test
  void update_shouldUpdateEntityWithSelectedId() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    assertNotNull(organizationEntity);
    var user = getMockObject(User.class).setRole(Role.SUPERUSER).setId(null).setOrganization(organizationEntity);
    var userEntity = userService.create(user);
    assertNotNull(userEntity);
    var tutor = getMockObject(Tutor.class).setId(null).setUser(userEntity).setSubjects(null);
    var tutorEntity = tutorService.create(tutor);
    assertNotNull(tutorEntity);
    var schedule = getMockObject(TutorSchedule.class).setId(null).setTutor(null);
    var scheduleEntity = tutorScheduleService.createSchedule(schedule, tutorEntity.getId());
    assertNotNull(scheduleEntity);
    var scheduleId = scheduleEntity.getId();
    var newSchedule = getMockObject(TutorSchedule.class).setId(null)
        .setTimeStart(OffsetTime.of(7, 0, 0, 0, ZoneOffset.UTC))
        .setTimeEnd(OffsetTime.of(9, 0, 0, 0, ZoneOffset.UTC));
    tutorScheduleService.update(newSchedule, scheduleId);
    var newScheduleEntity = assertDoesNotThrow(() -> tutorScheduleService.findById(scheduleId));
    assertNotEquals(newScheduleEntity, scheduleEntity);
    assertEquals(newScheduleEntity, newSchedule.setId(scheduleId));
  }
}
