package org.example.crmedu.application.dto.response.tutor;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.application.dto.response.user.GetUserResponse;
import org.example.crmedu.domain.model.Link;

@Data
@Accessors
public class GetTutorSimpleResponse {

  private Long id;

  private GetUserResponse user;

  private List<Link> socials;

  private String notes;
}
