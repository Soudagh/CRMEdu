package org.example.crmedu.domain.service.organization;

import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.service.BaseCrudService;

/**
 * Service interface for managing {@link Organization} entities. Defines methods for CRUD operations and querying organizations.
 */
public interface OrganizationService extends BaseCrudService<Organization> {

  /**
   * Check if organization with this unique identifier exists.
   *
   * @param id the unique identifier of the organization
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no organization with the given ID is found
   */
  void checkExistanceById(Long id);
}
