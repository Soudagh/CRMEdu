package org.example.crmedu.domain.service.tutor;

import java.util.List;
import java.util.Set;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.service.BaseCrudService;

/**
 * Service interface for managing {@link Tutor} entities. Define methods for CRUD operations and querying tutors.
 */
public interface TutorService extends BaseCrudService<Tutor> {

  /**
   * Updates the list of subjects that a tutor can teach.
   *
   * @param subjects set of subjects
   * @param id the unique identifier of the tutor to update
   */
  void patchSubjects(Set<Subject> subjects, Long id);

  /**
   * Updates the list of education classes that a tutor can teach.
   *
   * @param grades set of grades
   * @param id the unique identifier of the tutor to update
   */
  void patchGrades(Set<Integer> grades, Long id);

  List<Lesson> getScheduleByUserId(Long userId);
}
