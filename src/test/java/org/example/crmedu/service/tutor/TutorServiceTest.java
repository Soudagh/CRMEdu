package org.example.crmedu.service.tutor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.exception.UniqueConstraintsException;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.repository.TutorRepository;
import org.example.crmedu.domain.service.tutor.TutorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link TutorServiceImpl}. This class verifies the behavior of tutor-related operations using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
public class TutorServiceTest extends BaseUnitTest {

  @InjectMocks
  private TutorServiceImpl tutorService;

  @Mock
  private TutorRepository tutorRepository;

  @Test
  void givenNotExistentId_whenFindById_shouldThrowEntityNotFoundException() {
    var tutorId = 1L;
    when(tutorRepository.findById(tutorId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> tutorService.findById(tutorId));
  }

  @Test
  void givenExistentId_whenFindById_shouldReturnTutor() {
    var tutor = getMockObject(Tutor.class);
    var tutorId = tutor.getId();
    when(tutorRepository.findById(tutorId)).thenReturn(Optional.of(tutor));

    var response = assertDoesNotThrow(() -> tutorService.findById(tutorId));
    assertEquals(tutorId, response.getId());
    assertEquals(tutor.getUser(), response.getUser());
  }

  @Test
  void givenTutorAlreadyLinkedToUser_whenCreateTutor_shouldThrowUniqueConstraintsException() {
    var tutor = getMockObject(Tutor.class).setId(null);
    when(tutorRepository.existsByUser(tutor)).thenReturn(true);

    assertThrows(UniqueConstraintsException.class, () -> tutorService.create(tutor));
  }

  @Test
  void givenTutorNotLinkedToUser_whenCreateTutor_shouldReturnTutorLinkedToUser() {
    var tutor = getMockObject(Tutor.class).setId(null);
    when(tutorRepository.existsByUser(tutor)).thenReturn(false);
    when(tutorRepository.create(tutor)).thenReturn(tutor.setId(1L));

    var response = assertDoesNotThrow(() -> tutorService.create(tutor));
    assertEquals(tutor.getUser(), response.getUser());
  }
}
