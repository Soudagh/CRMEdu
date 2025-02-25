package org.example.crmedu.service.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.student.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Integration tests for {@link StudentService}. This class verifies the integration of student-related operations within the application, ensuring correct
 * interactions with the database.
 */
public class StudentServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private StudentService studentService;

  @Autowired
  private OrganizationService organizationService;

  @Test
  void delete_shouldDeleteEntityById() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    var student = getMockObject(Student.class).setId(null).setOrganization(organizationEntity).setHex("#FFFFFF").setGrade(5);
    var studentEntity = studentService.create(student);
    assertNotNull(studentEntity);
    var studentId = studentEntity.getId();
    assertDoesNotThrow(() -> studentService.findById(studentId));
    studentService.deleteStudent(studentId);
    assertThrows(EntityNotFoundException.class, () -> studentService.findById(studentId));
  }

  @Test
  void update_shouldUpdateEntity() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    var student = getMockObject(Student.class).setId(null).setOrganization(organizationEntity).setHex("#FFFFFF").setGrade(5);
    var studentEntity = studentService.create(student);
    assertNotNull(studentEntity);
    var studentId = studentEntity.getId();
    var newStudent = getMockObject(Student.class).setId(null).setOrganization(organizationEntity).setHex("#FFFFF2").setGrade(9);
    studentService.updateStudent(newStudent, studentId);
    var updatedStudentEntity = assertDoesNotThrow(() -> studentService.findById(studentId));
    assertNotEquals(updatedStudentEntity, studentEntity);
  }
}
