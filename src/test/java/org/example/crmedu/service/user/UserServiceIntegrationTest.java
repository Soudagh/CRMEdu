package org.example.crmedu.service.user;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.example.crmedu.domain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
  void createUser_withRoleTutor_shouldCreateTutor() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    assertNotNull(organizationEntity.getId());
    var user = getMockObject(User.class).setId(null).
        setOrganization(organizationEntity).setRole(Role.TUTOR);
    var createdUser = userService.create(user);
    assertNotNull(createdUser.getId());
    assert (tutorService.findAll(1, 1).getTotalCount() > 0);
  }

  @Test
  void delete_shouldDeleteUserWithSelectedId() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    assertNotNull(organizationEntity);
    var user = getMockObject(User.class).setId(null).setOrganization(organizationEntity);
    var userEntity = userService.create(user);
    assertNotNull(userEntity);
    var userId = userEntity.getId();
    assertDoesNotThrow(() -> userService.findById(userId));
    userService.delete(userId);
    assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
  }

  @Test
  void update_shouldUpdateUserWithSelectedId() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    assertNotNull(organizationEntity.getId());
    var user = getMockObject(User.class).setId(null).setOrganization(organizationEntity);
    var userEntity = userService.create(user);
    assertNotNull(user);
    var userId = userEntity.getId();
    var updatedUser = getMockObject(User.class).setId(null).setOrganization(organizationEntity);
    userService.update(updatedUser, userId);
    var updatedUserEntity = assertDoesNotThrow(() -> userService.findById(userId));
    assertNotEquals(userEntity, updatedUserEntity);
  }
}
