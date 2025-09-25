package org.example.crmedu.infrastructure.repository.student;

import org.example.crmedu.infrastructure.entity.StudentEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for managing {@link StudentEntity} entities in the database. Extends {@link JpaRepository} and {@link PagingAndSortingRepository} to provide
 * CRUD operations and pagination.
 */
@Repository
public interface DataStudentRepository extends BaseDataRepository<StudentEntity, Long> {

  StudentEntity getStudentEntityByUser_Id(Long userId);
}
