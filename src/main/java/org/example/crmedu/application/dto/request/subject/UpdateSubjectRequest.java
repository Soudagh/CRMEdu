package org.example.crmedu.application.dto.request.subject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a request to update an existing Subject.
 */
@Data
@Accessors(chain = true)
public class UpdateSubjectRequest {

  private String name;
}
