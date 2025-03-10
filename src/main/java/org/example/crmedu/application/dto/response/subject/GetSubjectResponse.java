package org.example.crmedu.application.dto.response.subject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing the response when retrieving a Subject.
 */
@Data
@Accessors(chain = true)
public class GetSubjectResponse {

  private Long id;

  private String name;

  private Long organization;
}
