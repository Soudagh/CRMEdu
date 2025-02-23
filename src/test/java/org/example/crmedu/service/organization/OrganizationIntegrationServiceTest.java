package org.example.crmedu.service.organization;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrganizationIntegrationServiceTest extends BaseIntegrationTest {

  @Autowired
  private OrganizationService organizationService;


  @Test
  void delete_shouldDeleteEntityWithSelectedId() {
    var organization = getMockObject(Organization.class).setId(null);
    var createdOrganization = organizationService.create(organization);
    assertNotNull(createdOrganization);
    var organizationId = createdOrganization.getId();
    organizationService.delete(organizationId);
    assertThrows(EntityNotFoundException.class, () -> organizationService.checkExistanceById(organizationId));
  }

  @Test
  void update_shouldUpdateEntityWithSelectedId() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    assertNotNull(organizationEntity);
    var organizationId = organizationEntity.getId();
    var updatedOrganization = getMockObject(Organization.class).setId(null);
    assertDoesNotThrow(() -> organizationService.update(updatedOrganization, organizationId));
    updatedOrganization.setId(organizationId);
    var updatedOrganizationEntity = organizationService.findById(organizationId);
    assertEquals(updatedOrganizationEntity, updatedOrganization);
    assertNotEquals(updatedOrganizationEntity, organizationEntity);
  }
}
