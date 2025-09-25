package org.example.crmedu.service.organization;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.exception.UniqueConstraintsException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.example.crmedu.domain.service.organization.OrganizationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link OrganizationService}. This class verifies the behavior of organization-related operations using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
public class OrganizationServiceTest extends BaseUnitTest {

  @InjectMocks
  private OrganizationServiceImpl organizationService;

  @Mock
  private OrganizationRepository organizationRepository;

  @Test
  void givenNotExistentOrganizationId_whenFindById_shouldThrowEntityNotFoundException() {
    var organizationId = 1L;
    when(organizationRepository.findById(organizationId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> organizationService.findById(organizationId));
  }

  @Test
  void givenNotExistentOrganizationId_whenCheckExistanceById_shouldThrowEntityNotFoundException() {
    var organizationId = 1L;
    when(organizationRepository.existsById(organizationId)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> organizationService.checkExistanceById(organizationId));
  }

  @Test
  void givenExistentOrganizationId_whenCheckExistanceById_shouldDoesNotThrowException() {
    var organizationId = 1L;
    when(organizationRepository.existsById(organizationId)).thenReturn(true);

    assertDoesNotThrow(() -> organizationService.checkExistanceById(organizationId));
  }

  @Test
  void givenTwoOrganizationsInRepository_whenFindAll_shouldReturnPageWithOrganizations() {
    int pageNumber = 0;
    int pageSize = 5;
    var org1 = getMockObject(Organization.class);
    var org2 = getMockObject(Organization.class);
    var organizations = List.of(org1, org2);
    var mockPage = new Page<Organization>()
        .setContent(organizations)
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalCount(organizations.size());
    when(organizationRepository.findAll(pageNumber, pageSize)).thenReturn(mockPage);

    var resultPages = organizationService.findAll(pageNumber, pageSize);

    assertNotNull(resultPages);
    assertEquals(organizations.size(), resultPages.getContent().size());
    assertEquals(org1, resultPages.getContent().get(0));
    assertEquals(org2, resultPages.getContent().get(1));
  }

  @Test
  void givenExistentOrganizationByEmail_whenCreateOrganizationWithThisEmail_shouldThrowUniqueConstraintsException() {
    var organization = getMockObject(Organization.class).setId(null);
    when(organizationRepository.existsByEmail(organization)).thenReturn(true);

    assertThrows(UniqueConstraintsException.class, () -> organizationService.create(organization));
  }

  @Test
  void givenExistentOrganizationByPhone_whenCreateOrganizationWithThisPhone_shouldThrowUniqueConstraintsException() {
    var organization = getMockObject(Organization.class).setId(null);
    when(organizationRepository.existsByPhone(organization)).thenReturn(true);

    assertThrows(UniqueConstraintsException.class, () -> organizationService.create(organization));
  }

  @Test
  void givenExistentOrganizationByName_whenCreateOrganizationWithThisName_shouldThrowUniqueConstraintsException() {
    var organization = getMockObject(Organization.class).setId(null);
    when(organizationRepository.existsByName(organization)).thenReturn(true);

    assertThrows(UniqueConstraintsException.class, () -> organizationService.create(organization));
  }

  @Test
  void givenOrganizationWithNotExistentConstraints_whenCreate_shouldCreateOrganization() {
    var organization = getMockObject(Organization.class).setId(null);
    when(organizationRepository.existsByName(organization)).thenReturn(false);
    when(organizationRepository.existsByPhone(organization)).thenReturn(false);
    when(organizationRepository.existsByEmail(organization)).thenReturn(false);
    when(organizationRepository.create(organization)).thenReturn(organization.setId(1L));

    var response = assertDoesNotThrow(() -> organizationService.create(organization));

    assertNotNull(response);
    assertEquals(organization.getName(), response.getName());
    assertEquals(organization.getEmail(), response.getEmail());
    assertEquals(organization.getPhone(), response.getPhone());
  }
}
