package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Subject {

  private Long id;

  private String name;
}
