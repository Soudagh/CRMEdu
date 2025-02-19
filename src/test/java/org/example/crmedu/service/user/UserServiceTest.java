package org.example.crmedu.service.user;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.example.crmedu.domain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceTest extends BaseIntegrationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private TutorService tutorService;

  @Autowired
  private OrganizationService organizationService;

  @Test
  void createUser_withRoleTutor_shouldCreateTutor() {
    var organization = new Organization().setName("a").setSpecialization("a").setEmail("organization@example.com").setPhone("+79999999999");
    var organizationEntity = organizationService.create(organization);
    assertNotNull(organizationEntity.getId());
    var user = new User().setSurname("a").setName("a").
        setOrganization(organizationEntity).setRole(Role.TUTOR).setEmail("tutor@example.com").setPhone("+79999999999");
    var createdUser = userService.create(user);
    assertNotNull(createdUser.getId());
    assert (tutorService.findAll(1, 1).getTotalCount() > 0);
  }
}
