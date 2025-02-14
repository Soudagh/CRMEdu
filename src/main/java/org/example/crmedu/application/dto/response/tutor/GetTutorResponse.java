package org.example.crmedu.application.dto.response.tutor;

import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.model.Link;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.model.TutorSchedule;
import org.example.crmedu.domain.model.User;

/**
 * A DTO representing the response when retrieving a Tutor.
 */
@Data
@Accessors(chain = true)
public class GetTutorResponse {

  private Long id;

  private User user;

  private List<Link> socials;

  private String notes;

  private Set<Subject> subjects;

  private Set<Integer> grades;

  private Set<TutorSchedule> schedule;
}
