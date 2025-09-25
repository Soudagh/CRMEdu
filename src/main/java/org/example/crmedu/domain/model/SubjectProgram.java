package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SubjectProgram {

  private Long id;

  private Program program;

  private Subject subject;

//  private List<Lesson> lessons = new ArrayList<>();
}
