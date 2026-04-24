package org.example.crmedu.service.user;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.enums.UserStatus;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.example.crmedu.domain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 * Integration tests for {@link UserService}. This class verifies the integration of user-related operations within the application, ensuring correct
 * interactions with the database.
 */
class UserServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private TutorService tutorService;

  @Autowired
  private OrganizationService organizationService;

  @Test
  void givenUserWithTutorRole_whenCreateUser_shouldCreateTutor() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    var user = getMockObject(User.class).setId(null).setHex("#333333").
        setOrganization(organizationEntity).setRole(Role.TUTOR);

    var createdUser = userService.create(user);

    var tutors = tutorService.findAll(0, 100);
    var tutor = tutors.getContent().getFirst();
    assertEquals(createdUser.getId(), tutor.getUser().getId());
  }

  @Test
  void givenExistingUser_whenDeleteUser_shouldDeleteAndNotFindUser() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    var user = getMockObject(User.class).setId(null).setOrganization(organizationEntity).setHex("#333333");
    var userEntity = userService.create(user);
    var userId = userEntity.getId();

    userService.delete(userId);

    assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
  }

  @Test
  void givenUpdatedUser_whenUpdateUser_shouldNotBeEqualWithPrevious() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    var user = getMockObject(User.class).setId(null).setOrganization(organizationEntity).setHex("#333333").setNotifications(new ArrayList<>());
    var userEntity = userService.create(user);
    var userId = userEntity.getId();
    var updatedUser = getMockObject(User.class).setId(null).setOrganization(organizationEntity).setHex("#333335").setNotifications(new ArrayList<>());

    userService.update(updatedUser, userId);

    var updatedUserEntity = assertDoesNotThrow(() -> userService.findById(userId));
    assertNotEquals(userEntity, updatedUserEntity);
  }

  @Test
  void givenUserWithCorrectVerificationToken_whenVerifyToken_shouldChangeUserStatusToActive() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    var user = getMockObject(User.class).setId(null).setOrganization(organizationEntity).setStatus(UserStatus.PENDING).setHex("#333333");
    var userEntity = userService.create(user);
    var userToken = userEntity.getVerificationToken();

    userService.verifyUserByVerificationToken(userToken);

    var updatedUser = assertDoesNotThrow(() -> userService.findById(userEntity.getId()));
    assertEquals(UserStatus.ACTIVE, updatedUser.getStatus());
  }
}
