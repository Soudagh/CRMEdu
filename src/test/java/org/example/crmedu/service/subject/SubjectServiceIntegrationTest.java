package org.example.crmedu.service.subject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.subject.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Integration tests for {@link SubjectService}. This class verifies the integration of subject-related operations within the application, ensuring correct
 * interactions with the database.
 */
public class SubjectServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private SubjectService subjectService;

  @Autowired
  private OrganizationService organizationService;

  @Test
  void delete_shouldDeleteEntityWithSelectedId() {
    var organization = getMockObject(Organization.class).setId(null);
    var organizationEntity = organizationService.create(organization);
    assertNotNull(organizationEntity);
    var subject = getMockObject(Subject.class).setId(null).setOrganization(organizationEntity);
    var subjectEntity = subjectService.create(subject);
    assertNotNull(subjectEntity);
    var subjectId = subjectEntity.getId();
    assertDoesNotThrow(() -> subjectService.findById(subjectId));
    subjectService.delete(subjectId);
    assertThrows(EntityNotFoundException.class, () -> subjectService.findById(subjectId));
  }
}
