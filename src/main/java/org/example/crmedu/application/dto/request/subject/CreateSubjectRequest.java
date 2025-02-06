package org.example.crmedu.application.dto.request.subject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a request to create a new Subject.
 */
@Data
@Accessors(chain = true)
public class CreateSubjectRequest {

  private String name;
}
