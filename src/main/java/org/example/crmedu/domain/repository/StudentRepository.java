package org.example.crmedu.domain.repository;

import java.util.Optional;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Student;

/**
 * Repository interface for managing {@link Student} entities. Defines methods for CRUD operations and querying students.
 */
public interface StudentRepository {

  /**
   * Saves a new student or updates an existing one.
   *
   * @param student the student to save
   * @return the saved {@link Student} entity
   */
  Student save(Student student);

  /**
   * Retrieves a student by its unique identifier.
   *
   * @param id the unique identifier of the student
   * @return an {@link Optional} containing the student if found, otherwise empty
   */
  Optional<Student> findById(Long id);

  /**
   * Checks whether a student with this email and phone exists in this organization.
   *
   * @param student the student to check
   * @return {@code true} if a student with the same email and phone exists in this organization, otherwise {@code false}
   */
  boolean existsByEmailAndPhoneInOrganization(Student student);

  /**
   * Retrieves a paginated list of students.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of students per page
   * @return a {@link Page} containing the requested students
   */
  Page<Student> findAll(int pageNumber, int pageSize);

  /**
   * Updates an existing student.
   *
   * @param student the student entity with updated information
   */
  void update(Student student);

  /**
   * Deletes a student by its unique identifier.
   *
   * @param id the unique identifier of the student to delete
   */
  void delete(Long id);
}
