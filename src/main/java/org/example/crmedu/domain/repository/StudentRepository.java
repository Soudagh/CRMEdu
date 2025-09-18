package org.example.crmedu.domain.repository;

import org.example.crmedu.domain.model.Student;

/**
 * Repository interface for managing {@link Student} entities. Defines methods for CRUD operations and querying students.
 */
public interface StudentRepository extends BaseCrudRepository<Student> {

  Student getStudentByUserId(Long userId);
}
