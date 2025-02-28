package org.example.crmedu.service.user;

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
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.domain.repository.UserRepository;
import org.example.crmedu.domain.service.jwt.PasswordEncode;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.example.crmedu.domain.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link UserServiceImpl}. This class verifies the behavior of user-related operations using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends BaseUnitTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private OrganizationRepository organizationRepository;

  @Mock
  private TutorService tutorService;

  @Mock
  private PasswordEncode passwordEncode;

  @Test
  void findById_shouldThrowException_whenSelectedIdNotFound() {
    var userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
  }

  @Test
  void findById_shouldNotThrowException_whenSelectedIdExists() {
    var user = getMockObject(User.class);
    var userId = user.getId();
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    assertDoesNotThrow(() -> userService.findById(userId));
  }

  @Test
  void create_shouldThrowException_whenUserWithThisEmailExists() {
    var user = getMockObject(User.class).setId(null);
    when(userRepository.existsByEmail(user)).thenReturn(true);
    assertThrows(EntityExistsException.class, () -> userService.create(user));
  }

  @Test
  void create_shouldThrowException_whenUserWithThisPhoneExists() {
    var user = getMockObject(User.class).setId(null);
    when(userRepository.existsByPhone(user)).thenReturn(true);
    assertThrows(EntityExistsException.class, () -> userService.create(user));
  }

  @Test
  void create_shouldThrowException_whenOrganizationNotExists() {
    var user = getMockObject(User.class).setId(null);
    when(organizationRepository.existsById(user.getOrganization().getId())).thenReturn(false);
    assertThrows(EntityNotFoundException.class, () -> userService.create(user));
  }

  @Test
  void create_shouldCreateUser() {
    var user = getMockObject(User.class);
    var userId = user.getId();
    user.setId(null);
    when(organizationRepository.existsById(user.getOrganization().getId())).thenReturn(true);
    when(userRepository.save(user)).thenReturn(user.setId(userId));
    var userEntity = userService.create(user);
    assertNotNull(userEntity);
  }

  @Test
  void findAll_shouldReturnPageOfUsers() {
    int pageNumber = 0;
    int pageSize = 5;
    var user1 = getMockObject(User.class);
    var user2 = getMockObject(User.class);
    var users = List.of(user1, user2);
    var mockPage = new Page<User>()
        .setContent(users)
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalCount(users.size());
    when(userRepository.findAll(pageNumber, pageSize)).thenReturn(mockPage);
    var resultPages = userService.findAll(pageNumber, pageSize);
    assertNotNull(resultPages);
    assertEquals(2, resultPages.getContent().size());
    assertEquals(user1, resultPages.getContent().get(0));
    assertEquals(user2, resultPages.getContent().get(1));
  }
}
