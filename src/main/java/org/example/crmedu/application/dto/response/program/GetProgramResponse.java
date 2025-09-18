package org.example.crmedu.application.dto.response.program;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.ProgramStatus;
import org.example.crmedu.domain.model.Organization;

@Data
@Accessors(chain = true)
public class GetProgramResponse {

  private Long id;

  private Organization organization;

  private String name;

  private Integer lessonCount;

  private Integer price;

  private ProgramStatus programStatus;

  private String hex;
}
