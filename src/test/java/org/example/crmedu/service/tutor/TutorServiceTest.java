package org.example.crmedu.service.tutor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.repository.TutorRepository;
import org.example.crmedu.domain.service.tutor.TutorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TutorServiceTest extends BaseUnitTest {

  @InjectMocks
  private TutorServiceImpl tutorService;

  @Mock
  private TutorRepository tutorRepository;

  @Test
  void findById_shouldThrowException_whenSelectedIdNotFound() {
    var tutorId = 1L;
    when(tutorRepository.findById(tutorId)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> tutorService.findById(tutorId));
  }

  @Test
  void findById_shouldNotThrowException_whenSelectedIdExists() {
    var tutor = getMockObject(Tutor.class);
    var tutorId = tutor.getId();
    when(tutorRepository.findById(tutorId)).thenReturn(Optional.of(tutor));
    assertDoesNotThrow(() -> tutorService.findById(tutorId));
  }

  @Test
  void create_shouldThrowException_whenTutorAlreadyBelongsToUser() {
    var tutor = getMockObject(Tutor.class).setId(null);
    when(tutorRepository.existsByUser(tutor)).thenReturn(true);
    assertThrows(EntityExistsException.class, () -> tutorService.create(tutor));
  }

  @Test
  void create_shouldNotThrowException() {
    var tutor = getMockObject(Tutor.class).setId(null);
    when(tutorRepository.existsByUser(tutor)).thenReturn(false);
    assertDoesNotThrow(() -> tutorService.create(tutor));
  }
}
