package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.ProgramStatus;

@Data
@Accessors(chain = true)
public class Program {

  private Long id;

  private Organization organization;

  private String name;

  private Integer lessonCount;

  private Integer price;

  private ProgramStatus programStatus;

  private String hex;
}
