package org.example.crmedu.domain.service.student;

import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Student;

/**
 * Service interface for managing {@link Student} entities. Defines methods for CRUD operations and querying students.
 */
public interface StudentService {

  /**
   * Creates a new student.
   *
   * @param student the student to create
   * @return the created {@link Student}
   * @throws org.example.crmedu.domain.exception.EntityExistsException if a student with the same email and phone exists in certain organization
   */
  Student create(Student student);

  /**
   * Retrieves a student by its unique identifier.
   *
   * @param id the unique identifier of the student
   * @return the found {@link Student}
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no student with the given ID is found
   */
  Student findById(Long id);

  /**
   * Retrieves a paginated list of students.
   *
   * @param pageNumber the page number (starting from 0)
   * @param pageSize the number of students per page
   * @return a {@link Page} containing the requested students
   */
  Page<Student> getStudents(int pageNumber, int pageSize);

  /**
   * Updates an existing student
   *
   * @param student the updated student data
   * @param id the unique identifier of the student to update
   * @throws org.example.crmedu.domain.exception.EntityNotFoundException if no student with the given ID is found
   * @throws org.example.crmedu.domain.exception.EntityExistsException if a student with the same email and phone already exists in that organization
   */
  void updateStudent(Student student, Long id);

  /**
   * Deletes a student by its unique identifier.
   *
   * @param id the unique identifier of the student to delete
   */
  void deleteStudent(Long id);
}
