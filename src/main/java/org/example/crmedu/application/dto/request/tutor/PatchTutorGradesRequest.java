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
public class PatchTutorGradesRequest {

  @NotNull
  private Set<Integer> grades = new HashSet<>();
}
