package org.example.crmedu.application.dto.request.tutor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.model.Link;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.model.TutorSchedule;

/**
 * A DTO representing a request to create a new Tutor.
 */
@Data
@Accessors(chain = true)
public class CreateTutorRequest {

  @NotNull
  @PositiveOrZero
  private Long user;

  private List<Link> socials;

  private String notes;

  private Set<Subject> subjects;

  private Set<Integer> grades;

  private Set<TutorSchedule> schedule;
}
