package org.example.crmedu.service.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.repository.StudentRepository;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.student.StudentServiceImpl;
import org.example.crmedu.domain.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link StudentServiceImpl}. This class verifies the behavior of student-related operations using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest extends BaseUnitTest {

  @InjectMocks
  private StudentServiceImpl studentService;

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private OrganizationService organizationService;

  @Test
  void create_shouldThrowException_whenStudentWithEmailAndPhoneExists() {
    var student = getMockObject(Student.class).setId(null);
    when(studentRepository.existsByEmailAndPhoneInOrganization(student)).thenReturn(true);
    assertThrows(EntityExistsException.class, () -> studentService.create(student));
  }

  @Test
  void create_shouldCreateStudent() {
    var organization = getMockObject(Organization.class);
    var student = getMockObject(Student.class).setId(null).setOrganization(organization);
    when(studentRepository.existsByEmailAndPhoneInOrganization(student)).thenReturn(false);
    when(studentRepository.save(student)).thenReturn(student.setId(1L));
    var studentEntity = assertDoesNotThrow(() -> studentService.create(student));
    assertNotNull(studentEntity);
    assertNotNull(studentEntity.getId());
  }

  @Test
  void findById_shouldThrowException() {
    var studentId = 1L;
    when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> studentService.findById(studentId));
  }

  @Test
  void findById_shouldReturnStudent() {
    var student = getMockObject(Student.class);
    when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
    assertDoesNotThrow(() -> studentService.findById(student.getId()));
  }

  @Test
  void findAll_shouldReturnPageOfStudents() {
    int pageNumber = 0;
    int pageSize = 5;
    var student1 = getMockObject(Student.class);
    var student2 = getMockObject(Student.class);
    var students = List.of(student1, student2);
    var mockPage = new Page<Student>()
        .setContent(students)
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalCount(students.size());
    when(studentRepository.findAll(pageNumber, pageSize)).thenReturn(mockPage);
    var resultPages = studentService.getStudents(pageNumber, pageSize);
    assertNotNull(resultPages);
    assertEquals(2, resultPages.getContent().size());
    assertEquals(student1, resultPages.getContent().get(0));
    assertEquals(student2, resultPages.getContent().get(1));
  }
}