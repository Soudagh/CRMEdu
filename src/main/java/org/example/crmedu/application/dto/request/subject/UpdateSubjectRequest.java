package org.example.crmedu.application.dto.request.subject;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateSubjectRequest {

  private String name;
}
