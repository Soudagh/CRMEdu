package org.example.crmedu.infrastructure.repository.tutor;

import org.example.crmedu.infrastructure.entity.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for managing {@link TutorEntity} entities in the database. Extends {@link JpaRepository} and {@link PagingAndSortingRepository} to provide
 * CRUD operations and pagination.
 */
@Repository
public interface DataTutorRepository extends JpaRepository<TutorEntity, Long>, PagingAndSortingRepository<TutorEntity, Long> {

  boolean existsByUserIdAndIdIsNot(Long userId, Long tutorId);
}
