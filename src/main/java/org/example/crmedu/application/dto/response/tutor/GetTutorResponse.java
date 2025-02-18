package org.example.crmedu.application.dto.response.tutor;

import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.application.dto.response.subject.GetSubjectResponse;
import org.example.crmedu.application.dto.response.user.GetUserResponse;
import org.example.crmedu.domain.model.Link;

/**
 * A DTO representing the response when retrieving a Tutor.
 */
@Data
@Accessors(chain = true)
public class GetTutorResponse {

  private Long id;

  private GetUserResponse user;

  private List<Link> socials;

  private String notes;

  private Set<GetSubjectResponse> subjects;

  private Set<Integer> grades;
}
