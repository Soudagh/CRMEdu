package org.example.crmedu.infrastructure.repository.student;

import org.example.crmedu.infrastructure.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for managing {@link StudentEntity} entities in the database. Extends {@link JpaRepository} and {@link PagingAndSortingRepository} to provide
 * CRUD operations and pagination.
 */
@Repository
public interface DataStudentRepository extends CrudRepository<StudentEntity, Long>, PagingAndSortingRepository<StudentEntity, Long> {

  /**
   * Checks whether a student with the specified phone and email that belongs to certain organization exists.
   *
   * @param email the email of the student
   * @param phone the phone of the student
   * @param organizationId the unique identifier of the organization
   * @param userId the unique identifier of the user
   * @return {@code true} if a student with this email and phone in this organization exists. Otherwise {@code false}
   */
  boolean existsByEmailAndPhoneAndOrganization_IdAndIdIsNot(String email, String phone, Long organizationId, Long userId);
}
