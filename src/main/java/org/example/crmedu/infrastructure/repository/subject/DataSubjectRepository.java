package org.example.crmedu.infrastructure.repository.subject;

import org.example.crmedu.infrastructure.entity.SubjectEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for managing {@link SubjectEntity} entities in the database. Extends {@link JpaRepository} and {@link PagingAndSortingRepository} to provide
 * CRUD operations and pagination.
 */
@Repository
public interface DataSubjectRepository extends BaseDataRepository<SubjectEntity, Long> {

  /**
   * Checks whether a subject with the specified name that belongs to certain organization exists.
   *
   * @param name the name of the subject
   * @param id the unique identifier of the organization
   * @return {@code true} if a subject with this name exists. Otherwise {@code false}
   */
  boolean existsByNameAndOrganizationId(String name, Long id);
}
