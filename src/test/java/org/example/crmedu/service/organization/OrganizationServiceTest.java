package org.example.crmedu.service.organization;

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
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.domain.service.organization.OrganizationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceTest extends BaseUnitTest {

  @InjectMocks
  private OrganizationServiceImpl organizationService;

  @Mock
  private OrganizationRepository organizationRepository;

  @Test
  void findById_shouldThrowException_whenSelectIdDoesNotExist() {
    var organizationId = 1L;
    when(organizationRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> organizationService.findById(organizationId));
  }

  @Test
  void checkExistanceById_shouldThrowException_whenReturnsFalse() {
    var organizationId = 1L;
    when(organizationRepository.existsById(organizationId)).thenReturn(false);
    assertThrows(EntityNotFoundException.class, () -> organizationService.checkExistanceById(organizationId));
  }

  @Test
  void checkExistanceById_shouldNotThrowException_whenReturnsTrue() {
    var organizationId = 1L;
    when(organizationRepository.existsById(organizationId)).thenReturn(true);
    assertDoesNotThrow(() -> organizationService.checkExistanceById(organizationId));
  }

  @Test
  void findAll_shouldReturnPageOfOrganizations() {
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
    assertEquals(2, resultPages.getContent().size());
    assertEquals(org1, resultPages.getContent().get(0));
    assertEquals(org2, resultPages.getContent().get(1));
  }

  @Test
  void checkOrganizationConstraint_shouldThrowException_whenOrganizationEmailExists() {
    var organization = getMockObject(Organization.class).setId(null);
    when(organizationRepository.existsByEmail(organization)).thenReturn(true);
    assertThrows(EntityExistsException.class, () -> organizationService.create(organization));
  }

  @Test
  void checkOrganizationConstraint_shouldThrowException_whenOrganizationPhoneExists() {
    var organization = getMockObject(Organization.class).setId(null);
    when(organizationRepository.existsByPhone(organization)).thenReturn(true);
    assertThrows(EntityExistsException.class, () -> organizationService.create(organization));
  }

  @Test
  void checkOrganizationConstraint_shouldThrowException_whenOrganizationNameExists() {
    var organization = getMockObject(Organization.class).setId(null);
    when(organizationRepository.existsByName(organization)).thenReturn(true);
    assertThrows(EntityExistsException.class, () -> organizationService.create(organization));
  }

  @Test
  void checkOrganizationConstraint_shouldNotThrowException_whenOrganizationUnique() {
    var organization = getMockObject(Organization.class).setId(null);
    when(organizationRepository.existsByName(organization)).thenReturn(false);
    when(organizationRepository.existsByPhone(organization)).thenReturn(false);
    when(organizationRepository.existsByEmail(organization)).thenReturn(false);
    assertDoesNotThrow(() -> organizationService.create(organization));
  }

}
