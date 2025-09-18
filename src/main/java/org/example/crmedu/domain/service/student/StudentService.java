package org.example.crmedu.domain.service.student;

import java.util.List;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.service.BaseCrudService;

/**
 * Service interface for managing {@link Student} entities. Defines methods for CRUD operations and querying students.
 */
public interface StudentService extends BaseCrudService<Student> {

  List<Lesson> getScheduleByUserId(Long userId);

  Student getStudentByUserId(Long userId);
}
