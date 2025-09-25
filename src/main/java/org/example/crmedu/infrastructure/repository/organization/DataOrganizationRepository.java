package org.example.crmedu.infrastructure.repository.organization;

import org.example.crmedu.infrastructure.entity.OrganizationEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for managing {@link OrganizationEntity} entities in the database. Extends {@link JpaRepository} and {@link PagingAndSortingRepository} to
 * provide CRUD operations and pagination.
 */
@Repository
public interface DataOrganizationRepository extends BaseDataRepository<OrganizationEntity, Long> {

  /**
   * Checks whether an organization with the specified name exists.
   *
   * @param name the name of the organization
   * @param id the identifier of the organization;
   * @return {@code true} if an organization with this name exists. otherwise {@code false}
   */
  boolean existsByNameAndIdIsNot(String name, Long id);

  /**
   * Checks whether an organization with the specified name exists.
   *
   * @param name the name of the organization
   * @return {@code true} if an organization with this name exists. otherwise {@code false}
   */
  boolean existsByName(String name);

  /**
   * Checks whether an organization with the specified email exists.
   *
   * @param email the email of the organization
   * @param id the identifier of the organization;
   * @return {@code true} if an organization with this email exists. otherwise {@code false}
   */
  boolean existsByEmailAndIdIsNot(String email, Long id);

  /**
   * Checks whether an organization with the specified email exists.
   *
   * @param email the email of the organization
   * @return {@code true} if an organization with this email exists. otherwise {@code false}
   */
  boolean existsByEmail(String email);

  /**
   * Checks whether an organization with the specified phone exists.
   *
   * @param phone the phone of the organization
   * @return {@code true} if an organization with this phone exists. otherwise {@code false}
   */
  boolean existsByPhoneAndIdIsNot(String phone, Long id);

  /**
   * Checks whether an organization with the specified phone exists.
   *
   * @param phone the phone of the organization
   * @return {@code true} if an organization with this phone exists. otherwise {@code false}
   */
  boolean existsByPhone(String phone);
}
