package org.example.crmedu.service.subject;

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
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.repository.SubjectRepository;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.subject.SubjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link SubjectServiceImpl}. This class verifies the behavior of subject-related operations using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest extends BaseUnitTest {

  @InjectMocks
  private SubjectServiceImpl subjectService;

  @Mock
  private SubjectRepository subjectRepository;

  @Mock
  private OrganizationService organizationService;

  @Test
  void findById_shouldThrowException_whenSelectedIdNotFound() {
    var subjectId = 1L;
    when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> subjectService.findById(subjectId));
  }

  @Test
  void findById_shouldThrowException_whenSelectedIdExists() {
    var subject = getMockObject(Subject.class);
    when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
    assertDoesNotThrow(() -> subjectService.findById(subject.getId()));
  }

  @Test
  void create_shouldThrowException_whenSubjectExistsInOrganization() {
    var subject = getMockObject(Subject.class).setId(null);
    when(subjectRepository.existsByNameAndOrganizationId(subject)).thenReturn(true);
    assertThrows(EntityExistsException.class, () -> subjectService.create(subject));
  }

  @Test
  void findAll_shouldReturnPageOfSubjects() {
    int pageNumber = 0;
    int pageSize = 5;
    var subject1 = getMockObject(Subject.class);
    var subject2 = getMockObject(Subject.class);
    var subjects = List.of(subject1, subject2);
    var mockPage = new Page<Subject>()
        .setContent(subjects)
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalCount(subjects.size());
    when(subjectRepository.findAll(pageNumber, pageSize)).thenReturn(mockPage);
    var resultPages = subjectService.findAll(pageNumber, pageSize);
    assertNotNull(resultPages);
    assertEquals(2, resultPages.getContent().size());
    assertEquals(subject1, resultPages.getContent().get(0));
    assertEquals(subject2, resultPages.getContent().get(1));
  }
}
