package org.example.crmedu.application.dto.request.tutor;

import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.model.Link;
import org.example.crmedu.domain.model.Subject;

/**
 * A DTO representing a request to update Tutor.
 */
@Data
@Accessors(chain = true)
public class UpdateTutorRequest {

  private List<Link> socials;

  private String notes;

  private Set<Long> subjects;

  private Set<Integer> grades;
}
