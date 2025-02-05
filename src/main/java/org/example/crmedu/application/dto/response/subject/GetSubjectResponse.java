package org.example.crmedu.application.dto.response.subject;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetSubjectResponse {

  private Long id;

  private String name;
}
