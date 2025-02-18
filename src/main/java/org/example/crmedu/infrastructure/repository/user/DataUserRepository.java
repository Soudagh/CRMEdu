package org.example.crmedu.infrastructure.repository.user;

import org.example.crmedu.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA repository for managing {@link UserEntity} entities in the database. Extends {@link JpaRepository} and {@link PagingAndSortingRepository} to provide CRUD
 * operations and pagination.
 */
@Repository
public interface DataUserRepository extends JpaRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

  /**
   * Checks whether a user with the specified email exists.
   *
   * @param email the email of the user
   * @return {@code true} if an user with this email exists. otherwise {@code false}
   */
  boolean existsByEmail(String email);

  /**
   * Checks whether a user with the specified phone exists.
   *
   * @param phone the email of the user
   * @return {@code true} if a user with this phone exists. otherwise {@code false}
   */
  boolean existsByPhone(String phone);
}
