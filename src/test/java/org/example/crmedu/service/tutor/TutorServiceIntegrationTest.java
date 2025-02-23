package org.example.crmedu.service.tutor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.subject.SubjectService;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.example.crmedu.domain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TutorServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private TutorService tutorService;

  @Autowired
  private OrganizationService organizationService;

  @Autowired
  private UserService userService;

  @Autowired
  private SubjectService subjectService;

  @Test
  void update_shouldUpdateEntityWithSelectedId() {
    var organization = organizationService.create(getMockObject(Organization.class).setId(null));
    var user = userService.create(getMockObject(User.class).setId(null).setOrganization(organization).setRole(Role.SUPERUSER));
    var tutor = getMockObject(Tutor.class).setId(null).setUser(user).setSubjects(null).setGrades(null);
    var tutorEntity = tutorService.create(tutor);
    assertNotNull(tutorEntity);
    var tutorId = tutorEntity.getId();
    var updatedTutor = getMockObject(Tutor.class).setId(null).setGrades(null).setSubjects(null).setUser(null);
    assertDoesNotThrow(() -> tutorService.update(updatedTutor, tutorId));
    updatedTutor.setId(tutorId).setUser(user);
    var updatedTutorEntity = tutorService.findById(tutorId);
    assert (!updatedTutorEntity.equals(tutorEntity));
  }

  @Test
  void delete_shouldDeleteEntityWithSelectedId() {
    var organization = organizationService.create(getMockObject(Organization.class).setId(null));
    var user = userService.create(getMockObject(User.class).setId(null).setOrganization(organization).setRole(Role.SUPERUSER));
    var tutor = getMockObject(Tutor.class).setId(null).setUser(user).setSubjects(null).setGrades(null);
    var tutorEntity = tutorService.create(tutor);
    assertNotNull(tutorEntity);
    var tutorId = tutorEntity.getId();
    tutorService.delete(tutorId);
    assertThrows(EntityNotFoundException.class, () -> tutorService.findById(tutorId));
  }

  @Test
  void patchSubjects_shouldUpdateSubjectsByTutorsId() {
    var organization = organizationService.create(getMockObject(Organization.class).setId(null));
    var user = userService.create(getMockObject(User.class).setId(null).setOrganization(organization).setRole(Role.SUPERUSER));
    var tutor = getMockObject(Tutor.class).setId(null).setUser(user).setSubjects(null).setGrades(null);
    var subject = getMockObject(Subject.class).setId(null).setOrganization(organization);
    var subject1 = getMockObject(Subject.class).setId(null).setOrganization(organization);
    var subjectEntity = subjectService.create(subject);
    var subjectEntity1 = subjectService.create(subject1);
    var tutorEntity = tutorService.create(tutor);
    assertNotNull(tutorEntity);
    var tutorId = tutorEntity.getId();
    var subjects = Set.of(subjectEntity, subjectEntity1);
    var previousSubjects = tutorEntity.getSubjects();
    tutorService.patchSubjects(subjects, tutorId);
    var updatedTutor = tutorService.findById(tutorId);
    assertNotNull(updatedTutor);
    var currentSubjects = updatedTutor.getSubjects();
    assertEquals(currentSubjects, subjects);
    assertNotEquals(currentSubjects, previousSubjects);
  }

  @Test
  void patchGrandes_shouldUpdateGradesByTutorsId() {
    var organization = organizationService.create(getMockObject(Organization.class).setId(null));
    var user = userService.create(getMockObject(User.class).setId(null).setOrganization(organization).setRole(Role.SUPERUSER));
    var tutor = getMockObject(Tutor.class).setId(null).setUser(user).setSubjects(null).setGrades(null);
    var tutorEntity = tutorService.create(tutor);
    assertNotNull(tutorEntity);
    var tutorId = tutorEntity.getId();
    var grades = Set.of(1, 2, 3);
    var previousGrades = tutorEntity.getGrades();
    tutorService.patchGrades(grades, tutorId);
    var updatedTutor = tutorService.findById(tutorId);
    assertNotNull(updatedTutor);
    var currentGrades = updatedTutor.getGrades();
    assertEquals(currentGrades, grades);
    assertNotEquals(currentGrades, previousGrades);
  }
}
