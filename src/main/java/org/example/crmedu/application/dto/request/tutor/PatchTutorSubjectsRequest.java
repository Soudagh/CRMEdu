package org.example.crmedu.application.dto.request.tutor;

import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a request to change grades that tutor can teach.
 */
@Data
@Accessors(chain = true)
public class PatchTutorSubjectsRequest {

  @NotNull
  private Set<Long> subjects = new HashSet<>();
}
