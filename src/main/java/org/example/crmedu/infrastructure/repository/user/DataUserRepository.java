package org.example.crmedu.infrastructure.repository.user;

import java.util.Optional;
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
   * @param email the email of the user * @param id the id of the user
   * @return {@code true} if a user with this email exists. otherwise {@code false}
   */
  boolean existsByEmailAndIdIsNot(String email, Long id);

  /**
   * Checks whether a user with the specified phone exists.
   *
   * @param phone the email of the user
   * @param id the id of the user
   * @return {@code true} if a user with this phone exists. otherwise {@code false}
   */
  boolean existsByPhoneAndIdIsNot(String phone, Long id);

  /**
   * Retrieves user by his email.
   *
   * @param email the email of the user
   * @return an {@link Optional} containing the user if found, otherwise empty
   */
  Optional<UserEntity> findByEmail(String email);

  /**
   * Retrieves user by his verification token.
   *
   * @param verificationToken the verification token of the user
   * @return an {@link Optional} containing the user if found, otherwise empty
   */
  Optional<UserEntity> findByVerificationToken(String verificationToken);
}
